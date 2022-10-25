class ItemService(val itemRepository: ItemRepository ) {

    constructor():this(ItemRepository())

    fun selectRandomItems (nr: Int): MutableList<Item>{
        val randomItems = mutableListOf<Item>()
        var number = nr
        if (nr > itemRepository.size()){
            number = itemRepository.size()
        }
        if (number <= 0){
            println("The number can't be 0 or less")
            return randomItems
        }
        while(randomItems.size != number) {
            val randQuestion = itemRepository.randomItem()
            if (!randomItems.contains(randQuestion)) {
                randomItems.add(randQuestion)
            }
        }
        return randomItems
    }
}
