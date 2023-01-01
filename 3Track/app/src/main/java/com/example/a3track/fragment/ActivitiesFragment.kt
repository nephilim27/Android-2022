package com.example.a3track.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.databinding.FragmentActivitiesBinding
import com.example.a3track.databinding.FragmentMyTasksBinding
import com.example.a3track.model.Activity
import com.example.a3track.model.DataAdapter
import com.example.a3track.model.DataAdapterActivities
import com.example.a3track.model.Task
import com.example.a3track.repository.TrackerRepository
import com.example.a3track.viewModel.ActivityViewModel
import com.example.a3track.viewModel.ActivityViewModelFactory
import com.example.a3track.viewModel.TaskViewModel
import com.example.a3track.viewModel.TaskViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivitiesFragment : Fragment(), DataAdapterActivities.OnItemClickListener {

    private var list: List<Activity> = mutableListOf()
    lateinit var adapter: DataAdapterActivities
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentActivitiesBinding


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

        activityViewModel.activityList.observe(viewLifecycleOwner){
            list = activityViewModel.activityList.value!!
            Log.d("activities", list.toString())

            adapter = DataAdapterActivities(list, this)
            recyclerView.adapter = adapter
        }
        activityViewModel.readActivities()

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
    }

    private fun initViewItems(){
        recyclerView = binding.activityList
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Activity = list[position]
        clickedItem.Note = "Clicked"
        adapter.notifyItemChanged(position)
    }

}