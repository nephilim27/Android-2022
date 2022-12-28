package com.example.a3track.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.model.User
import com.example.a3track.repository.TrackerRepository
import kotlinx.coroutines.launch

//class UserListViewModelFactory(
//    private val repository: TrackerRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return UserListViewModel( repository) as T
//    }
//}
//
//class UserListViewModel(val repository: TrackerRepository): ViewModel() {
//
//    var userList = MutableLiveData<List<User>>()
//
//    fun readUsers() {
//        viewModelScope.launch {
//            try {
//                val response = repository.getUsers(MyApplication.token)
//                if(response.isSuccessful) {
//                    userList.value = response.body()
//                } else{
//                    Log.i("xxx-uvm", response.message())
//                }
//            } catch (e: Exception) {
//                Log.i("xxx", e.toString())
//            }
//        }
//    }
//}