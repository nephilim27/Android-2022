package com.example.quizapp.util

import com.example.quizapp.ItemList
import com.example.quizapp.R


class Utils {
    fun generateDummyList(size: Int): List<ItemList> {
        val list = ArrayList<ItemList>()
        for (i in 0 until size) {
            val item = ItemList("Item $i")
            list += item
        }
        return list
    }
}