open class ItemService : ItemRepository() {

    fun selectRandomItems (nr: Int): List<Item>{
        val randomItems = mutableListOf<Item>()
        repeat(nr){
            randomItems.add(randomItem())
        }
        return randomItems
    }


}