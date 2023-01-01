package com.example.a3track.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a3track.R
import com.example.a3track.databinding.FragmentAddTaskBinding
import com.example.a3track.databinding.FragmentMyTasksBinding
import com.example.a3track.model.Task
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.TaskViewModel
import com.example.a3track.viewModel.TaskViewModelFactory


class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var add: Button
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var time: EditText
    private lateinit var assigner: EditText
    private lateinit var assignee: EditText
    private lateinit var priority: EditText
    private lateinit var deadline: EditText
    private lateinit var department: EditText
    private lateinit var status: EditText
    private lateinit var percentage: EditText
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TaskViewModelFactory(TrackerRepository())
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()
        registerListeners()
    }

    private fun initViewItems(){
        add = binding.addButton
        title = binding.titleInput
        description = binding.descInput
        time = binding.timeInput
        assigner = binding.assignerInput
        assignee = binding.assigneeInput
        priority = binding.priorityInput
        deadline = binding.deadLineInput
        department = binding.departmentInput
        status = binding.statusInput
        percentage = binding.progressInput
        back = binding.backToTasks

    }

    private fun registerListeners(){
        add.setOnClickListener {

                val task = Task(ID = 1,
                        title = title.text.toString(),
                        description = description.text.toString(),
                        time = time.text.toString().toInt(),
                        assigner = assigner.text.toString().toInt(),
                        assignee = assignee.text.toString().toInt(),
                        priority = priority.text.toString().toInt(),
                        deadline = deadline.text.toString().toLong(),
                        departmentID = department.text.toString().toInt(),
                        status = status.text.toString().toInt(),
                        percentage = percentage.text.toString().toInt())


            taskViewModel.addTasks(task)
            taskViewModel.taskList.postValue(listOf(task))
            Log.d("tasks", taskViewModel.taskList.toString())

            title.text.clear()
            description.text.clear()
            time.text.clear()
            assigner.text.clear()
            assignee.text.clear()
            priority.text.clear()
            deadline.text.clear()
            department.text.clear()
            status.text.clear()
            percentage.text.clear()
        }

        back.setOnClickListener {
            replaceFragment(MyTasksFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}