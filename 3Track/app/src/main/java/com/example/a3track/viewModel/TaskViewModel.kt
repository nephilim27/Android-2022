package com.example.a3track.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.Util.RequestState
import com.example.a3track.model.LoginResult
import com.example.a3track.model.Task
import com.example.a3track.repository.TrackerRepository
import kotlinx.coroutines.launch

class TaskViewModelFactory(
    private val repository: TrackerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel( repository) as T
    }
}

class TaskViewModel(private val repository: TrackerRepository): ViewModel() {

    var taskList = MutableLiveData<List<Task>>()
    val getTasksState: MutableLiveData<RequestState> = MutableLiveData()


    fun readTasks() {
        getTasksState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                val response = repository.getTasks(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        Log.d("responseBody", response.body().toString())
                        taskList.postValue(response.body())
                        getTasksState.value = RequestState.SUCCESS
                    } else{
                        Log.i("xxx-uvm", response.message())
                        getTasksState.value = RequestState.UNKNOWN_ERROR
                    }
                }
            } catch (e: Exception) {
                Log.i("task", e.toString())
                getTasksState.value = RequestState.UNKNOWN_ERROR

            }
        }
    }

    fun addTasks(task: Task) {
        viewModelScope.launch {
            try {
                val response = repository.createTask(task)
                if (response != null) {
                    if(response.isSuccessful) {
                        taskList.postValue(response.body())
                    } else{
                        Log.i("xxx-uvm", response.message())
                    }
                }
            } catch (e: Exception) {
                Log.i("task", e.toString())
            }
        }
    }
}