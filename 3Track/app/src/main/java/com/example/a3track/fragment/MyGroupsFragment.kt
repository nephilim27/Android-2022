package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.R
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentMyGroupsBinding
import com.example.a3track.model.DataAdapterGroups
import com.example.a3track.models.DepartmentModel
import com.example.a3track.viewModel.GlobalViewModel


class MyGroupsFragment : Fragment(), DataAdapterGroups.OnItemClickListener {
    private val globalViewModel: GlobalViewModel by viewModels()
    lateinit var dataAdapter: DataAdapterGroups
    private lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentMyGroupsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyGroupsBinding.inflate(inflater, container, false)

        recyclerView = binding.groupList
        dataAdapter = DataAdapterGroups(ArrayList(), this)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(context)
        }
        recyclerView.setHasFixedSize(true)

        globalViewModel.readDepartments()
        globalViewModel.requestState.observe(viewLifecycleOwner) {
            if (it == RequestState.SUCCESS) {
                dataAdapter.setDate(globalViewModel.getDepartmentList().value!! as MutableList<DepartmentModel>)
            }
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        val depId = globalViewModel.getDepartmentList().value!![position].ID
        bundle.putInt("departmentID", depId)
        val fragment = GroupMembersFragment()
        fragment.arguments = bundle
        replaceFragment(fragment)
    }
}