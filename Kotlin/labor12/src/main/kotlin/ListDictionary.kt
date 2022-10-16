import java.io.File

object ListDictionary : IDictionary {

    private val words = mutableListOf<String>()

    init {
        val filename = "dict.txt"
        File(filename).forEachLine { add(it) }
    }

    override fun add (word: String): Boolean{
        return words.add(word)
    }
    override fun find (word: String): Boolean{
        return words.contains(word)
    }

    override fun size(): Int {
        return words.size
    }

}