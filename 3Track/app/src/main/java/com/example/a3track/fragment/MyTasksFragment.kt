package com.example.a3track.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentMyTasksBinding
import com.example.a3track.model.*
import com.example.a3track.models.TaskModel
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.GlobalViewModel
import com.example.a3track.viewModel.TaskViewModel
import com.example.a3track.viewModel.TaskViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class MyTasksFragment : Fragment(), TaskDataAdapter.OnItemClickListener {

    lateinit var dataAdapter: TaskDataAdapter
    lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentMyTasksBinding
    lateinit var add: ImageView
    private val globalViewModel: GlobalViewModel by viewModels()
    private lateinit var taskFilter: Spinner

    private var taskList: List<TaskModel> = listOf()

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

        initViewItems()
        registerListeners()

        val taskCategories = listOf("All", "Completed", "In Progress", "Not started", "Blocked")
        val taskAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, taskCategories)
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskFilter.adapter = taskAdapter

        dataAdapter = TaskDataAdapter(ArrayList(), this)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        recyclerView.setHasFixedSize(true)

        globalViewModel.LoadTasks()
        globalViewModel.requestState.observe(viewLifecycleOwner) {
            if(it == RequestState.SUCCESS) {
                Log.d("taskListFragment", globalViewModel.getTaskList().value.toString())
                taskList = globalViewModel.getTaskList().value!!
                dataAdapter.setData(globalViewModel.getTaskList().value!! as MutableList<TaskModel>)
                onTaskFilterChanged()
            }
        }

        return binding.root
    }

    private fun onTaskFilterChanged() {
        taskFilter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currentCategory = parent?.getItemAtPosition(position).toString()

                if(currentCategory == "All") {
                    dataAdapter.setData(taskList as MutableList<TaskModel>)
                } else {
                    when(currentCategory) {
                        "Completed" -> dataAdapter.setData(taskList.filter { task -> task.status == 3 } as MutableList<TaskModel>)
                        "In Progress" -> dataAdapter.setData(taskList.filter { task -> task.status == 1 } as MutableList<TaskModel>)
                        "Not started" -> dataAdapter.setData(taskList.filter { task -> task.status == 0 } as MutableList<TaskModel>)
                        "Blocked" -> dataAdapter.setData(taskList.filter { task -> task.status == 2 } as MutableList<TaskModel>)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                dataAdapter.setData(taskList as MutableList<TaskModel>)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewItems(){
        recyclerView = binding.tasksList
        add = binding.addTask
        taskFilter = binding.taskFilter
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
        val taskId = dataAdapter.getItemAt(position).ID
        val parsedValue = Gson().toJson(dataAdapter.getItemAt(position), object: TypeToken<TaskModel>() {}.type)
        val bundle = Bundle()
        bundle.putString("currentTask", parsedValue)
        val fragment = TaskDeatilsFragment()
        fragment.arguments = bundle
        replaceFragment(fragment)
    }
}