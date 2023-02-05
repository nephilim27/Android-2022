package com.example.a3track.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentAddTaskBinding
import com.example.a3track.model.CreateTask
import com.example.a3track.viewModel.GlobalViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    private lateinit var back: ImageView
    private lateinit var projectSpinner: Spinner
    private lateinit var assignedSpinner: Spinner
    private lateinit var prioritySpinner: Spinner
    private lateinit var taskName: EditText
    private lateinit var dateSelect: ImageView
    private lateinit var taskDesc: EditText
    private lateinit var createTaskButton: Button
    private lateinit var deadlineText: TextView
    private val globalViewModel: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        initViewItems()
        registerListeners()

        taskName.hint = "Enter task name here"
        taskDesc.hint = "Task Description"

        taskDesc.setScroller(Scroller(requireContext()))
        taskDesc.maxLines = 10
        taskDesc.isVerticalScrollBarEnabled = true
        taskDesc.movementMethod = ScrollingMovementMethod()

        val prioritySpinnerData = arrayOf("Low", "Medium", "High")
        val priorityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioritySpinnerData)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = priorityAdapter

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(today)

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select a deadline")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        // on deadline select click listener
        dateSelect.setOnClickListener {
            datePicker.show(childFragmentManager, "DATE_PICKER")

            // get the selected date
            datePicker.addOnPositiveButtonClickListener {
                val date = datePicker.headerText
                deadlineText.text = date
            }
        }


        globalViewModel.loadUsersDepartments()
        globalViewModel.requestState.observe(viewLifecycleOwner) { state ->
            if (state == RequestState.SUCCESS) {
                val userList = globalViewModel.getUserList()
                val projectList = globalViewModel.getDepartmentList()

                val userAdapterData = userList.value?.map { it.first_name + " " + it.last_name }
                val userAdapter = context?.let { ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userAdapterData!!
                )}
                userAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                assignedSpinner.adapter = userAdapter

                val projectAdapterData = projectList.value?.map { it.name }

                val currentUser = globalViewModel.getCurrentUser()
                val currentUserDepartment = globalViewModel.getCurrentUserDepartment()

                if(currentUser?.type != 0) {
                    val projectAdapter = context?.let { ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listOf(currentUserDepartment?.name!!)
                    )}
                    projectAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    projectSpinner.isEnabled = false
                    projectSpinner.isClickable = false
                    projectSpinner.adapter = projectAdapter
                } else {
                    val projectAdapter = context?.let { ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        projectAdapterData!!
                    )}
                    projectAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    projectSpinner.adapter = projectAdapter
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initViewItems(){
        projectSpinner = binding.projectSpinner
        assignedSpinner = binding.assigneeSpinner
        prioritySpinner = binding.prioritySpinner
        taskName = binding.taskName
        dateSelect = binding.selectDate
        taskDesc = binding.taskDesc
        createTaskButton = binding.createTaskButton
        deadlineText = binding.deadline
        back = binding.backToTasks

    }

    private fun registerListeners(){
        createTaskButton.setOnClickListener {
            val taskName = taskName.text.toString()
            val taskDesc = taskDesc.text.toString()
            val priority = prioritySpinner.selectedItem.toString()
            val assigned = assignedSpinner.selectedItem.toString()
            val project = projectSpinner.selectedItem.toString()
            // convert deadline text to unix timestamp
            val deadline = deadlineText.text.toString()

            if(deadline == "Pick a deadline:") {
                Toast.makeText(requireContext(), "Please select a deadline", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val str_date = deadlineText.text.toString()
            val formatter = SimpleDateFormat("MMMM dd, yyyy")
            val date: Date = formatter.parse(str_date) as Date

            if(taskName.isEmpty()) {
                val snackBar = Snackbar.make(requireView(), "Task name cannot be empty", Snackbar.LENGTH_SHORT)
                snackBar.anchorView = binding.createTaskButton
                snackBar.show()
                return@setOnClickListener
            }

            if(taskDesc.isEmpty()) {
                val snackBar = Snackbar.make(requireView(), "Task description cannot be empty", Snackbar.LENGTH_SHORT)
                snackBar.anchorView = binding.createTaskButton
                snackBar.show()
                return@setOnClickListener
            }

            val assignedToUserId = globalViewModel.getUserList().value?.find { it.first_name + " " + it.last_name == assigned }?.ID
            val departmentId = globalViewModel.getDepartmentList().value?.find { it.name == project }?.ID
            val priorityId = when(priority) {
                "Low" -> 1
                "Medium" -> 2
                "High" -> 3
                else -> 1
            }

            val createRequest = CreateTask(
                title = taskName,
                description = taskDesc,
                priority = priorityId,
                assignedToUserId = assignedToUserId!!,
                departmentId = departmentId!!,
                deadline = date.time / 1000,
                status = 1
            )

            globalViewModel.createTask(createRequest)
            globalViewModel.requestState.observe(viewLifecycleOwner) { state ->
                if (state == RequestState.SUCCESS) {
                    val snackBar = Snackbar.make(requireView(), "Task created successfully", Snackbar.LENGTH_SHORT)
                    snackBar.anchorView = binding.createTaskButton
                    snackBar.show()
                    Log.d("CreateTaskFragment", "Task created successfully")
                    replaceFragment(MyTasksFragment())
                }
            }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback : OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Exit")
                builder.setMessage("Are you sure you want to exit? You will lose all the data you entered.")
                builder.setPositiveButton("Yes") { _, _ ->
                    taskName.setText("")
                    taskDesc.setText("")
                    prioritySpinner.setSelection(0)
                    assignedSpinner.setSelection(0)
                    projectSpinner.setSelection(0)
                    replaceFragment(MyTasksFragment())
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}