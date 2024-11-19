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
        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()
        val dtoClassName = "${className}Dto"

        val properties = classDeclaration.getAllProperties().map {
            PropertySpec.builder(it.simpleName.asString(), it.type.toTypeName())
                .initializer(it.simpleName.asString())
                .build()
        }.toList()

        val dtoClass = TypeSpec.classBuilder(dtoClassName)
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

        val functions = listOf(
            FunSpec.builder("to$dtoClassName")
                .receiver(classDeclaration.toClassName())
                .returns(ClassName(packageName, dtoClassName))
                .addCode(
                    "return %T(${
                        properties.joinToString(", ")
                        { "${it.name} = this.${it.name}" }
                    })",
                    ClassName(packageName, dtoClassName)
                )
                .build(),

            FunSpec.builder("to$className")
                .receiver(ClassName(packageName, dtoClassName))
                .returns(classDeclaration.toClassName())
                .addCode(
                    "return %T(${
                        properties.joinToString(", ")
                        { "${it.name} = this.${it.name}" }
                    })",
                    classDeclaration.toClassName()
                )
                .build()
        )

        val fileSpec = FileSpec.builder(packageName, dtoClassName)
            .addImport("kotlinx.serialization", "Serializable")
            .addType(dtoClass)
            .addFunctions(functions)
            .build()

        fileSpec.writeTo(
            codeGenerator, Dependencies(false, classDeclaration.containingFile!!)
        )
    }
}

class GeneratorConverterProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ConverterProcessor(environment.codeGenerator, environment.logger)
    }
}