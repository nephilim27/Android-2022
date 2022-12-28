package com.example.a3track.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.model.*
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.TaskViewModel
import com.example.a3track.viewModel.TaskViewModelFactory

class MyTasksFragment : Fragment(), DataAdapter.OnItemClickListener {

    private var list: List<Task> = mutableListOf()
    lateinit var adapter: DataAdapter
    lateinit var taskViewModel: TaskViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TaskViewModelFactory(TrackerRepository())
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tasks, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        taskViewModel.taskList.observe(viewLifecycleOwner) {
             list = taskViewModel.taskList.value!!
            Log.d("taskList", list.toString())

            adapter = DataAdapter(list, this)
            recyclerView.adapter = adapter
        }

        taskViewModel.readTasks()
        recyclerView = view.findViewById(R.id.tasksList)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Task = list[position]
        clickedItem.title = "Clicked"
        adapter.notifyItemChanged(position)
    }

}