package com.example.a3track.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.model.Activity
import com.example.a3track.model.LoginResult
import com.example.a3track.model.Task
import com.example.a3track.repository.TrackerRepository
import kotlinx.coroutines.launch

class ActivityViewModelFactory(
    private val repository: TrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityViewModel( repository) as T
    }
}

class ActivityViewModel(private val repository: TrackerRepository): ViewModel() {

    var activityList = MutableLiveData<List<Activity>>()

    fun readActivities() {
        viewModelScope.launch {
            try {
                val response = repository.getActivities(MyApplication.token)
                if (response != null) {
                    if (response.isSuccessful) {
                        activityList.postValue(response.body())
                    } else {
                        Log.i("xxx-uvm", response.message())
                    }
                }
            } catch (e: Exception) {
                Log.i("task", e.toString())
            }
        }
    }
}

