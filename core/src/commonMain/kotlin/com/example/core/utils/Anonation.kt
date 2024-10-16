package com.example.core.utils


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MessageDescription(val message: String)
