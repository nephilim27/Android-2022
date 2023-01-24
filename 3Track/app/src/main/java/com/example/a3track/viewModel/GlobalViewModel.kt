package com.example.a3track.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.a3track.Util.RequestState
import com.example.a3track.models.TaskModel
import com.example.a3track.models.UserModel
import com.example.a3track.repository.TrackerRepository

class GlobalViewModel(application: Application): AndroidViewModel(application) {

    private val taskViewModel: TaskViewModel by lazy { TaskViewModel(TrackerRepository()) }
    private val userViewModel: UserListViewModel by lazy { UserListViewModel(TrackerRepository())}

    val taskList: MutableLiveData<List<TaskModel>> = MutableLiveData()
    val requestState: MutableLiveData<RequestState> = MutableLiveData()

    fun LoadTasks(){
        val liveDataMerger = MediatorLiveData<Int>()
        requestState.value = RequestState.LOADING

        taskList.value = listOf()
        userViewModel.readUsers()
        taskViewModel.readTasks()
        liveDataMerger.value = 0
        liveDataMerger.addSource(userViewModel.readUserState){
            if (it == RequestState.SUCCESS){
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", userViewModel.userList.value.toString())
            }
        }
        liveDataMerger.addSource(taskViewModel.getTasksState){
            if (it == RequestState.SUCCESS){
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", taskViewModel.taskList.value.toString())
            }
        }
        liveDataMerger.observeForever{
            if (it == 2){
                val userList = userViewModel.userList.value
                val taskListTemp = taskViewModel.taskList.value
                Log.d("lists", userList.toString())
                Log.d("lists", taskListTemp.toString())

                for (task in taskListTemp!!){
                    val createdByUserTemp = userList?.find { user -> user.ID == task.assigner}
                    val assignToUserTemp = userList?.find { user -> user.ID == task.assignee }
                    val createdByUser =
                        createdByUserTemp?.let { it1 -> UserModel(it1.ID, createdByUserTemp.first_name, createdByUserTemp.last_name,
                        createdByUserTemp.email, createdByUserTemp.type, createdByUserTemp.location, createdByUserTemp.phone_number,
                        createdByUserTemp.department_id) }

                    val assignToUser =
                        assignToUserTemp?.let { it1 -> UserModel(it1.ID, it1.first_name, it1.last_name,
                            it1.email, it1.type, it1.location, it1.phone_number, it1.department_id) }

                    taskList.value = taskList.value?.plus(
                        TaskModel(task.ID, task.title, task.description, task.time, createdByUser!!, assignToUser!!,
                            task.priority, task.deadline, task.departmentID, task.status, task.percentage)
                    )
                }

                Log.d("taskLst", taskList.value.toString())
            }

        }
    }
}