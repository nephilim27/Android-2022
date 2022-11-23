package com.example.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.ItemList
import com.example.quizapp.R
import com.example.quizapp.itemList
import com.example.quizapp.models.DataAdapter
import com.example.quizapp.models.QuizViewModel


class QuestionListFragment : Fragment(), DataAdapter.detailsItem, DataAdapter.OnItemClickListener, DataAdapter.deleteItem { // List Item event handling
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var list: MutableList<ItemList>
    lateinit var adapter: DataAdapter
    lateinit var delete: Button
    lateinit var viewModel: QuizViewModel

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


        adapter = DataAdapter(list as MutableList<ItemList>, this, this, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        val clickedItem: ItemList = list[position]
        clickedItem.question = "Clicked"
        adapter.notifyItemChanged(position)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun detailsItem(position: Int) {
        replaceFragment(DetailFragment())
    }

    override fun deleteItem(position: Int){
        itemList.removeAt(position)
        adapter.notifyDataSetChanged()
    }

}
