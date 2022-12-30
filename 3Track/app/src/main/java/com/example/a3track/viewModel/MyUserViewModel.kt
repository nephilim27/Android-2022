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

class MyUserViewModelFactory(
    private val repository: TrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyUserViewModel( repository) as T
    }
}

class MyUserViewModel(private val repository: TrackerRepository): ViewModel() {

    lateinit var user: User

    fun readUser() {
        viewModelScope.launch {
            try {
                val response = repository.getMyUser(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        user = response.body()!!
                    } else{
                        Log.i("xxx-uvm", response.message())
                    }
                }
            } catch (e: Exception) {
                Log.i("xxx", e.toString())
            }
        }
    }
}