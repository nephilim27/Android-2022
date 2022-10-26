package com.example.quizapp.models

data class Item(val question: String, val answers: MutableList<String>, val correct: Int)

val item: MutableList<Item> = mutableListOf(
    Item(
        question = "Who developed Kotlin?",
        answers = mutableListOf("JetBrains", "Google", "Microsoft", "Oracle"),
        correct = 1
    ),
    Item(
        question = "Which extension is responsible to save Kotlin files?",
        answers = mutableListOf(".src", ".android", ".kot", ".kt or .kts"),
        correct = 4
    ),
    Item(
        question = "How to do a multi-line comment in Kotlin language?",
        answers = mutableListOf("//", "/* */", "\\", "%"),
        correct = 2
    ),
    Item(
        question = "The two types of constructors in Kotlin are?",
        answers = mutableListOf("First and second constructor", "Primary and secondary constructor", "Constant and Parameterized constructor", "None of these"),
        correct = 2
    ),
    Item(
        question = "What handles null exception in Kotlin?",
        answers = mutableListOf("Sealed classes", "Lambda functions", "Elvis operator", "The Kotlin extension"),
        correct = 3
    ),
    Item(
        question = "The correct function to get the length of a string in Kotlin is?",
        answers = mutableListOf("str.length", "lengthof(str)", "string(length)", "length(str)"),
        correct = 1
    ),
    Item(
        question = "The correct function to get the length of a string in Kotlin is?",
        answers = mutableListOf("str.length", "lengthof(str)", "string(length)", "length(str)"),
        correct = 1
    ),
    Item(
        question = "In Kotlin the default visibility operator is?",
        answers = mutableListOf("protected", "public", "private", "sealed"),
        correct = 2
    ),
    Item(
        question = "What defines sealed class in Kotlin?",
        answers = mutableListOf("It is used in every Kotlin program", "It's another name for abstract class", "It represents restricted class hierarchies", "None of these"),
        correct = 3
    ),
    Item(
        question = "The functions in Kotlin can be divided in how many types?",
        answers = mutableListOf("2", "3", "4", "None of these"),
        correct = 1
    ),
    Item(
        question = "Which are the basic data types in Kotlin?",
        answers = mutableListOf("Arrays and Booleans", "Characters", "Strings and Numbers", "All of these"),
        correct = 4
    )

)
