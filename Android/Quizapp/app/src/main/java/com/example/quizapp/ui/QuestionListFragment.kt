package com.example.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.ItemList
import com.example.quizapp.R
import com.example.quizapp.itemList
import com.example.quizapp.models.DataAdapter
import com.example.quizapp.models.OnItemClickListener
import com.example.quizapp.util.Utils

@Suppress("UNUSED_EXPRESSION")
class QuestionListFragment : Fragment(),
    OnItemClickListener { // List Item event handling
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var list: List<ItemList>
    lateinit var adapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = itemList
        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        // 1. No event handling
        // recycler_view.adapter = DataAdapter(list, this)
        // 2. Event handling - pass fragment (this) to data adapter
        adapter = DataAdapter(list as MutableList<ItemList>, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        var clickedItem : ItemList = list[position]
        clickedItem.question = "Clicked"
        adapter.notifyItemChanged(position)

    }
}