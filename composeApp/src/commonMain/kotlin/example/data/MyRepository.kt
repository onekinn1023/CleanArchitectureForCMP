package example.data

import org.koin.core.annotation.Single

interface MyRepository {

    fun helloWorld(): String
}

@Single
class MyRepositoryImpl: MyRepository {

    override fun helloWorld(): String {
       return "Hello World!"
    }
}