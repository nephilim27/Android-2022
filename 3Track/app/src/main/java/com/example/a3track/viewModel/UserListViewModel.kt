package com.example.a3track.viewModel

import android.app.DownloadManager.Request
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.Util.RequestState
import com.example.a3track.model.User
import com.example.a3track.repository.TrackerRepository
import kotlinx.coroutines.launch

class UserListViewModelFactory(
    private val repository: TrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserListViewModel( repository) as T
    }
}

class UserListViewModel(val repository: TrackerRepository): ViewModel() {

    var userList = MutableLiveData<List<User>>()
    val readUserState: MutableLiveData<RequestState> = MutableLiveData()

    fun readUsers() {
        readUserState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                val response = repository.getUsers(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        userList.postValue(response.body())
                        readUserState.value = RequestState.SUCCESS
                    } else{
                        Log.i("xxx-uvm", response.message())
                        readUserState.value = RequestState.UNKNOWN_ERROR
                    }
                }
            } catch (e: Exception) {
                Log.i("ajaj", e.toString())
                readUserState.value = RequestState.UNKNOWN_ERROR

            }
        }
    }
}