package example.data

interface MyRepository {

    fun helloWorld(): String
}

class MyRepositoryImpl: MyRepository {

    override fun helloWorld(): String {
       return "Hello World!"
    }
}