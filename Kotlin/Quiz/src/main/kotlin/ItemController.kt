class ItemController(val itemService: ItemService) {
    constructor():this(ItemService())

    fun quiz (nr: Int): Unit{
        var listOfQuestions = mutableListOf<Item>()
        var total = 0
        listOfQuestions = itemService.selectRandomItems(nr)

        listOfQuestions.forEach{ item ->
            println(item.question)
            item.answers.forEachIndexed { index, s ->
                println("${index+1}. $s")
            }
            println("Enter your answer: ")
            var answer = readLine()!!.toInt()
            if (answer == item.correct){
                total++
            }
            println()
        }
        println("$total out of ${listOfQuestions.size}")
    }
}