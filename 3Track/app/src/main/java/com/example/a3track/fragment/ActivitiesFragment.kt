package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentActivitiesBinding
import com.example.a3track.model.DataAdapterActivities
import com.example.a3track.models.ActivityModel
import com.example.a3track.models.DepartmentModel
import com.example.a3track.models.TaskModel
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.ActivityViewModel
import com.example.a3track.viewModel.ActivityViewModelFactory
import com.example.a3track.viewModel.GlobalViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivitiesFragment : Fragment(), DataAdapterActivities.OnItemClickListener {

    private var list: List<ActivityModel> = mutableListOf()
    lateinit var dataAdapter: DataAdapterActivities
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentActivitiesBinding
    private val globalViewModel: GlobalViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        val factory = ActivityViewModelFactory(TrackerRepository())
        activityViewModel = ViewModelProvider(this, factory)[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewItems()

        dataAdapter = DataAdapterActivities(ArrayList(), this)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(context)
        }
        recyclerView.setHasFixedSize(true)

        globalViewModel.loadActivities()
        globalViewModel.requestState.observe(viewLifecycleOwner) {
            if (it == RequestState.SUCCESS) {
                list = globalViewModel.getActivityList().value!!
                dataAdapter.setDate(globalViewModel.getActivityList().value!! as MutableList<ActivityModel>,
                    globalViewModel.getDepartmentList().value!! as MutableList<DepartmentModel>,
                    globalViewModel.getTaskList().value!! as MutableList<TaskModel>)
            }
        }
    }

    private fun initViewItems(){
        recyclerView = binding.activityList
    }

    override fun onItemClick(position: Int) {
        val clickedItem: ActivityModel = list[position]
        clickedItem.note = "Clicked"
        dataAdapter.notifyItemChanged(position)
    }

}