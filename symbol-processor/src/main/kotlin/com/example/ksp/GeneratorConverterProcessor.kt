package com.example.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class ConverterProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.example.core.utils.GenerateDto")

        val invalidSymbols =
            symbols.filterNot { it is KSClassDeclaration && it.classKind == ClassKind.CLASS }

        invalidSymbols.forEach {
            logger.error("@GenerateDto only for data class", it)
        }

        symbols.filterIsInstance<KSClassDeclaration>()
            .filter { it.modifiers.contains(Modifier.DATA) }
            .forEach { symbol ->
                processGenerateConverter(symbol)
            }
        return emptyList()
    }

    private fun processGenerateConverter(classDeclaration: KSClassDeclaration) {
        val packageName =
            classDeclaration.packageName.asString().replace(Regex("\\.domain"), ".data")
        val originClassName = classDeclaration.toClassName()
        val purposeClassName = ClassName(packageName, "${originClassName.simpleName}Dto")

        val properties = classDeclaration.getAllProperties().map {
            PropertySpec.builder(it.simpleName.asString(), it.type.toTypeName())
                .initializer(it.simpleName.asString())
                .build()
        }.toList()

        val dtoClass = TypeSpec.classBuilder(purposeClassName.simpleName)
            .addModifiers(KModifier.DATA)
            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(
                        properties.map { ParameterSpec.builder(it.name, it.type).build() }
                    )
                    .build()
            )
            .addProperties(properties)
            .build()

        val fileSpec = FileSpec.builder(packageName, purposeClassName.simpleName)
            .addImport("kotlinx.serialization", "Serializable")
            .addImport("com.example.network.utils", "CloudTypeConverter")
            .addType(dtoClass)
            .addType(
                generateCloudConverter(
                    originClassName,
                    purposeClassName,
                    properties
                )
            )
            .build()

        fileSpec.writeTo(
            codeGenerator, Dependencies(false, classDeclaration.containingFile!!)
        )
    }

    private fun generateCloudConverter(
        className: ClassName,
        dtoClassName: ClassName,
        properties: List<PropertySpec>
    ): TypeSpec {
        val superInterfacesClassName = ClassName("com.example.network.utils", "CloudTypeConverter")
        return TypeSpec.classBuilder("${className.simpleName}CloudTypeConverter")
            .addModifiers(KModifier.INTERNAL)
            .addSuperinterface(superInterfacesClassName.parameterizedBy(className, dtoClassName))
            .addFunction(
                FunSpec.builder("convertFromDto")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("dto", dtoClassName)
                    .returns(className)
                    .addCode(
                        "return %T(${
                            properties.joinToString(", ")
                            { "${it.name} = dto.${it.name}" }
                        })",
                        className
                    )
                    .build()
            )
            .addFunction(
                FunSpec.builder("convertToDto")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("domain", className)
                    .returns(dtoClassName)
                    .addCode(
                        "return %T(${
                            properties.joinToString(", ")
                            { "${it.name} = domain.${it.name}" }
                        })",
                        dtoClassName
                    )
                    .build()
            )
            .build()
    }
}

class GeneratorConverterProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ConverterProcessor(environment.codeGenerator, environment.logger)
    }
}