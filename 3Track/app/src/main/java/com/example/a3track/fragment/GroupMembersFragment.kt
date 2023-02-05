package com.example.a3track.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3track.Util.RequestState
import com.example.a3track.databinding.FragmentGroupMembersBinding
import com.example.a3track.model.DataAdapterUsers
import com.example.a3track.models.UserModel
import com.example.a3track.viewModel.GlobalViewModel


class GroupMembersFragment : Fragment(), DataAdapterUsers.OnItemClickListener {
   lateinit var binding : FragmentGroupMembersBinding
    private val globalViewModel: GlobalViewModel by viewModels()
    lateinit var dataAdapter: DataAdapterUsers
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupMembersBinding.inflate(inflater, container, false)

        recyclerView = binding.userList
        dataAdapter = DataAdapterUsers(ArrayList(), this)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(context)
        }
        recyclerView.setHasFixedSize(true)

        val depId = arguments?.getInt("departmentID")

        globalViewModel.readUsersGivenDepartmentID(depId!!)
        globalViewModel.requestState.observe(viewLifecycleOwner) {
            if (it == RequestState.SUCCESS) {
                dataAdapter.setDate(globalViewModel.getUsersGivenDepartmentID() as MutableList<UserModel>)
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {

    }

}