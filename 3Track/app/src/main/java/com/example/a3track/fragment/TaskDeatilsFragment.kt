package com.example.a3track.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.a3track.R
import com.example.a3track.databinding.FragmentTaskDeatilsBinding
import com.example.a3track.models.TaskModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// @TODO: finish details fragment and add back button to toolbar just like in add task fragment

class TaskDeatilsFragment : Fragment() {
    private var currentItem: TaskModel? = null
    private var _binding: FragmentTaskDeatilsBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskTitle: TextView
    private lateinit var taskDeadline: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDeatilsBinding.inflate(inflater, container, false)
        initViews()

        val parsedValue = arguments?.getString("currentTask")
        currentItem = Gson().fromJson(parsedValue, object : TypeToken<TaskModel>() {}.type)

        // @NOTE: this is how you access the data from the model
        // @WARNING: make sure to check if the data is null before accessing it and handle it accordingly

        taskTitle.text = currentItem?.title

        val deadline = currentItem?.deadline
        if (deadline != 0L) {
            // format deadline to human readable as dd/mm/yyyy
            @SuppressLint("SimpleDateFormat") val dateFormat =
                java.text.SimpleDateFormat("MMM dd yyyy")
            val deadlineDate = dateFormat.format((deadline ?: 0) * 1000)
            val text = "Deadline: <b>$deadlineDate</b>"
            taskDeadline.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            taskDeadline.text = "N/A"
        }

        backButton.setOnClickListener {
            replaceFragment(MyTasksFragment())
        }

        return binding.root
    }


    private fun initViews() {
        taskTitle = binding.taskTitleTMP
        taskDeadline = binding.taskDeadlineTMP
        backButton = binding.backToTasksButton
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}