fun main() {
    // Exercise 1
    sum(2, 3)
    println()

    //Exercise 2
    weekList()
    println()

    //Exercise 3
    println("Prime numbers between 2 and 100: ")
    range()
    println()

    //Exercise 4
    messageCoding("Kotlin", ::encode)
    println()

    //Exercise 6
    maps()
    println()

    //Exercise 7
    mutableLists()
    println()

    //Exercise 8
    arrays()
}

fun sum (a: Int, b: Int){
    println("$a + $b = ${a + b}")
}

fun weekList(){
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    daysOfWeek.forEach { print("$it ") }
    println()
    println("Days of the week starting with T:")
    daysOfWeek.filter { it.startsWith("T") }.forEach { print("$it ") }
    println()
    println("Days of the week containing e:")
    daysOfWeek.filter { it.contains("e") }.forEach { print("$it ") }
    println()
    println("Days of the week of length 6:")
    daysOfWeek.filter { it.length == 6 }.forEach { print("$it ") }
}

fun isPrime(num: Int) : Boolean{
    var flag = false
    for (i in 2..num / 2) {
        if (num % i == 0) {
            flag = true
            break
        }
    }
    return !flag
}

fun range(){
    (2..100).filter { isPrime(it) }.forEach { print("$it ") }
}

fun messageCoding(msg: String, func: (String) -> String): String{
    return func(msg)
}

fun encode(msg: String) : String {
    return ""
}

// fun even()

fun maps(){
    val numbers = listOf(1,2,3,4,5,6,7,8,9)
    println("Elements doubled: ")
    println(numbers.map { it*2 })
    println()

    val daysOfWeek = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")
    println("All days capitalized: ")
    println(daysOfWeek.map { it.toUpperCase() })
    println()

    println("First letter capitalized: ")
    println(daysOfWeek.map { it.capitalize() })
    println()

    println("Lengtgh of each day: ")
    println(daysOfWeek.map { it.length })
    println()

    var sum = 0.0
    daysOfWeek.forEach { sum += it.length }
    println("Average length:\n${sum/daysOfWeek.size}")
    println()
}

fun mutableLists(){
    val daysOfWeek = mutableListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    daysOfWeek.removeIf{ it.contains('n')}
    println("The list after removing elements: ")
    println(daysOfWeek)
    println()

    for ((index, value) in daysOfWeek.withIndex()) {
        println("Item at $index is $value")
    }
    println()

    daysOfWeek.sort()
    println("Sorted list: ")
    println(daysOfWeek)
}

fun arrays(){
    val array = Array(10) { (0..100).random()}
    println("Array with random numbers between 0 and 100: ")
    array.forEach { println(it) }
    println()

    array.sort()
    println("Sorted array: ")
    array.forEach { println(it) }
    println()

    val evens = array.filter { it % 2 == 0 }
    if (evens.size > 0){
        println("The array has even numbers")
    }
    else{
        print("The array doesn't have even numbers")
    }

    if (evens.size == array.size){
        println("All the numbers are even")
    }
    else{
        print("Not all numbers are even")
    }
    println()

    var sum = 0.0
    array.forEach { sum += it }
    println("The avaerage of the numbers is ${sum/array.size}")

}