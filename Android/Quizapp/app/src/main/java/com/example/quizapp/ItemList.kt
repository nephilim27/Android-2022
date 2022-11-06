package com.example.quizapp

data class ItemList (var question: String)

val itemList: MutableList<ItemList> = mutableListOf(
    ItemList(question = "Who developed Kotlin?"),
    ItemList(question = "Which extension is responsible to save Kotlin files?"),
    ItemList(question = "How to do a multi-line comment in Kotlin language?"),
    ItemList(question = "The two types of constructors in Kotlin are?"),
    ItemList(question = "What handles null exception in Kotlin?"),
    ItemList(question = "The correct function to get the length of a string in Kotlin is?"),
    ItemList(question = "The correct function to get the length of a string in Kotlin is?"),
    ItemList(question = "In Kotlin the default visibility operator is?"),
    ItemList(question = "What defines sealed class in Kotlin?"),
    ItemList(question = "The functions in Kotlin can be divided in how many types?"),
    ItemList(question = "Which are the basic data types in Kotlin?"))