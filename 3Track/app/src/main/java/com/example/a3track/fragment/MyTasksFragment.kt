package com.example.a3track.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.MyApplication
import com.example.a3track.R
import com.example.a3track.databinding.FragmentMyTasksBinding
import com.example.a3track.databinding.FragmentProfileBinding
import com.example.a3track.model.*
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.TaskViewModel
import com.example.a3track.viewModel.TaskViewModelFactory

class MyTasksFragment : Fragment(), DataAdapter.OnItemClickListener {

    private var list: List<Task> = mutableListOf()
    lateinit var adapter: DataAdapter
    lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentMyTasksBinding
    lateinit var add: ImageView
    private var details: ImageView? = view?.findViewById(R.id.taskDetails)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TaskViewModelFactory(TrackerRepository())
        taskViewModel = ViewModelProvider(requireActivity(), factory)[TaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = TaskViewModelFactory(TrackerRepository())
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()
        registerListeners()

        taskViewModel.taskList.observe(viewLifecycleOwner) {
             list = taskViewModel.taskList.value!!
            Log.d("taskList", list.toString())

            adapter = DataAdapter(list, this)
            recyclerView.adapter = adapter
        }
        taskViewModel.readTasks()

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)

        details?.setOnClickListener {
            replaceFragment(TaskDeatilsFragment())
        }

    }

    private fun initViewItems(){
        recyclerView = binding.tasksList
        add = binding.addTask

    }

    private fun registerListeners(){
        add.setOnClickListener {
            replaceFragment(AddTaskFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Task = list[position]
        clickedItem.title = "Clicked"
        adapter.notifyItemChanged(position)
    }

}