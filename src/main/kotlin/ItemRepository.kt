import java.io.File

open class ItemRepository {
    val items = mutableListOf<Item>()

    init {
        val lines = File("questions.txt").readLines()
        var i = 0

        while(i < lines.size){
           // val answerNumber = lines[i++].toInt()
            val question: String = lines[i++]
            val correct: Int = lines[i++].toInt()

            val answers = mutableListOf<String>()
            repeat(4){
                answers.add(lines[i++])
            }
            val item = Item(question, answers, correct)
            save(item)
        }
    }

    fun save(item: Item): Unit{
        items.add(item)
    }

    fun printItems(){
        for (item in items){
            println(item)
        }
    }

    fun size(): Int{
        return items.size
    }

    fun randomItem(): Item {
        return items.random()
    }
}