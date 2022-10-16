fun monogram (s: String){
    val delim = ' '
    val list = s.split(delim)
    val firstCharList = list.map { it.first() }
    println(firstCharList.joinToString(""))
}

fun joinedList(words: List<String>) = words.joinToString (separator = "#") {it}

 fun longestString(words: List<String>) = words.maxBy { it.length }


fun main(){
    //ex 1
    println("Monogram: ")
    monogram("John Smith")
    println()

    //ex2
    println("Strings joined: ")
    println(joinedList(listOf("apple", "pear", "melon")))
    println()

    //ex3
    println("Longest string: ")
    println(longestString(listOf("apple", "pear", "cherry")))
    println()
}