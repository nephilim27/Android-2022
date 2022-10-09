class ItemController : ItemService() {

    fun quiz (nr: Int): Unit{
        val items: ItemRepository = ItemRepository()
        selectRandomItems(nr)


    }
}