package com.example.a3track.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.a3track.MyApplication
import com.example.a3track.Util.RequestState
import com.example.a3track.model.Activity
import com.example.a3track.model.CreateTask
import com.example.a3track.models.ActivityModel
import com.example.a3track.models.DepartmentModel
import com.example.a3track.models.TaskModel
import com.example.a3track.models.UserModel
import com.example.a3track.repository.TrackerRepository

class GlobalViewModel(application: Application): AndroidViewModel(application) {

    private val taskViewModel: TaskViewModel by lazy { TaskViewModel(TrackerRepository()) }
    private val userViewModel: UserListViewModel by lazy { UserListViewModel(TrackerRepository())}
    private val departmentViewModel: DepartmentViewModel by lazy { DepartmentViewModel(
        TrackerRepository()
    ) }
    private val activityViewModel: ActivityViewModel by lazy { ActivityViewModel(TrackerRepository()) }
    private var currentUser: UserModel? = null

    private var taskList: MutableLiveData<List<TaskModel>> = MutableLiveData()
    private var activityList: MutableLiveData<List<ActivityModel>> = MutableLiveData()
    val requestState: MutableLiveData<RequestState> = MutableLiveData()

    fun LoadTasks(){
        val liveDataMerger = MediatorLiveData<Int>()
        requestState.value = RequestState.LOADING

        taskList.value = listOf()
        userViewModel.readUsers()
        taskViewModel.readTasks()
        departmentViewModel.getDepartments()

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

        liveDataMerger.addSource(departmentViewModel.getDepartmentsState) {
            if (it == RequestState.SUCCESS) {
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", "loadDepartments: ${liveDataMerger.value}")
            }
        }

        liveDataMerger.observeForever { it ->
            if (it == 3){
                val userList = userViewModel.userList.value
                val taskListTemp = taskViewModel.taskList.value
                val departmentList = departmentViewModel.departmentList.value
                val createdByUser =
                Log.d("lists", userList.toString())
                Log.d("lists", taskListTemp.toString())

                for (task in taskListTemp!!){
                    val createdByUserTemp = userList?.find { user -> user.ID == task.created_by_user_ID}
                    val assignToUserTemp = userList?.find { user -> user.ID == task.asigned_to_user_ID }
                    val department = departmentList?.find { department -> department.ID == task.department_ID }

                    val createdByUser = createdByUserTemp?.let { it1 -> UserModel(it1.ID, createdByUserTemp.first_name, createdByUserTemp.last_name,
                    createdByUserTemp.email, createdByUserTemp.type, createdByUserTemp.location, createdByUserTemp.phone_number,
                    createdByUserTemp.department_id, createdByUserTemp.image) }

                    val assignToUser =
                        assignToUserTemp?.let { it1 -> UserModel(it1.ID, it1.first_name, it1.last_name,
                            it1.email, it1.type, it1.location, it1.phone_number, it1.department_id, it1.image) }

                    taskList.value = taskList.value?.plus(
                        TaskModel(task.ID, task.title, task.description, task.created_time, createdByUser, assignToUser,
                            task.priority, task.deadline, department, task.status, task.percentage)
                    )
                }

                Log.d("taskLst", taskList.value.toString())

                requestState.value = RequestState.SUCCESS
            }

        }
    }

    fun loadActivities() {
        val liveDataMerger = MediatorLiveData<Int>()
        requestState.value = RequestState.LOADING

        activityList.value = listOf()
        taskList.value = listOf()

        userViewModel.readUsers()
        activityViewModel.readActivities()
        departmentViewModel.getDepartments()
        taskViewModel.readTasks()

        liveDataMerger.value = 0

        liveDataMerger.addSource(userViewModel.readUserState){
            if (it == RequestState.SUCCESS){
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", userViewModel.userList.value.toString())
            }
        }

        liveDataMerger.addSource(departmentViewModel.getDepartmentsState) {
            if (it == RequestState.SUCCESS) {
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", "loadDepartments: ${liveDataMerger.value}")
            }
        }

        liveDataMerger.addSource(activityViewModel.readActivitiesState) {
            if (it == RequestState.SUCCESS) {
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", "loadActivities: ${liveDataMerger.value}")
            }
        }

        liveDataMerger.addSource(taskViewModel.getTasksState){
            if (it == RequestState.SUCCESS){
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", taskViewModel.taskList.value.toString())
            }
        }

        liveDataMerger.observeForever {it ->
            if(it == 4) {
                val userList = userViewModel.userList.value
                val activityListTemp = activityViewModel.activityList.value
                val departmentList = departmentViewModel.departmentList.value
                val taskListTemp = taskViewModel.taskList.value
                Log.d("lists", userList.toString())
                Log.d("lists", activityListTemp.toString())

                for (task in taskListTemp!!){
                    val createdByUserTemp = userList?.find { user -> user.ID == task.created_by_user_ID}
                    val assignToUserTemp = userList?.find { user -> user.ID == task.asigned_to_user_ID }
                    val department = departmentList?.find { department -> department.ID == task.department_ID }

                    val createdByUser = createdByUserTemp?.let { it1 -> UserModel(it1.ID, createdByUserTemp.first_name, createdByUserTemp.last_name,
                        createdByUserTemp.email, createdByUserTemp.type, createdByUserTemp.location, createdByUserTemp.phone_number,
                        createdByUserTemp.department_id, createdByUserTemp.image) }

                    val assignToUser =
                        assignToUserTemp?.let { it1 -> UserModel(it1.ID, it1.first_name, it1.last_name,
                            it1.email, it1.type, it1.location, it1.phone_number, it1.department_id, it1.image) }

                    taskList.value = taskList.value?.plus(
                        TaskModel(task.ID, task.title, task.description, task.created_time, createdByUser, assignToUser,
                            task.priority, task.deadline, department, task.status, task.percentage)
                    )
                }

                for (activity in activityListTemp!!){
                    val createdByUserTemp = userList?.find { user -> user.ID == activity.created_by_user_id}
                    val assignToUserTemp = userList?.find { user -> user.ID == activity.sub_user_ID }

                    val createdByUser = createdByUserTemp?.let { it1 -> UserModel(it1.ID, createdByUserTemp.first_name, createdByUserTemp.last_name,
                        createdByUserTemp.email, createdByUserTemp.type, createdByUserTemp.location, createdByUserTemp.phone_number,
                        createdByUserTemp.department_id, createdByUserTemp.image) }

                    val assignToUser =
                        assignToUserTemp?.let { it1 -> UserModel(it1.ID, it1.first_name, it1.last_name,
                            it1.email, it1.type, it1.location, it1.phone_number, it1.department_id, it1.image) }

                    activityList.value = activityList.value?.plus(
                        ActivityModel(activity.ID, activity.type, activity.sub_type, createdByUser, activity.created_time, activity.sub_ID,
                        assignToUser, activity.note, activity.progress)
                    )
                }

                Log.d("activityLst", activityList.value.toString())

                requestState.value = RequestState.SUCCESS
            }
        }
    }

    fun getActivityList(): MutableLiveData<List<ActivityModel>> {
        return activityList
    }

    fun loadUsersDepartments() {
        val liveDataMerger = MediatorLiveData<Int>()
        requestState.value = RequestState.LOADING

        userViewModel.readUsers()
        departmentViewModel.getDepartments()

        liveDataMerger.value = 0
        liveDataMerger.addSource(userViewModel.readUserState){
            if (it == RequestState.SUCCESS){
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", userViewModel.userList.value.toString())
            }
        }

        liveDataMerger.addSource(departmentViewModel.getDepartmentsState) {
            if (it == RequestState.SUCCESS) {
                liveDataMerger.value = liveDataMerger.value?.plus(1)
                Log.d("lists1", "loadDepartments: ${liveDataMerger.value}")
            }
        }

        liveDataMerger.observeForever { it ->
            if (it == 2){
                currentUser = userViewModel.userList.value?.find { user -> user.ID == MyApplication.id!! }
                requestState.value = RequestState.SUCCESS
            }
        }
    }

   fun createTask(createTask: CreateTask) {
       requestState.value = RequestState.LOADING
       taskViewModel.addTasks(createTask)
       Log.d("createTask", "createTask: ${createTask}")
       taskViewModel.createTaskState.observeForever {
           if (it == RequestState.SUCCESS){
               requestState.value = RequestState.SUCCESS
               Log.d("createTask", "SUCCESS createTask: ${it}")
           }
       }
   }

    fun getTaskList() = taskList

    fun getUserList() = userViewModel.userList

    fun getDepartmentList() = departmentViewModel.departmentList

    fun getCurrentUser() = currentUser

    fun getCurrentUserDepartment(): DepartmentModel? {
        return departmentViewModel.departmentList.value?.find { department -> department.ID == currentUser?.department_id }
    }

    fun getCurrentUserFunc() {
        requestState.value = RequestState.LOADING
        userViewModel.getMyUser()
        userViewModel.readUserState.observeForever {
            if (it == RequestState.SUCCESS){
                val temp = userViewModel.getCurrentUser()!!
                currentUser = UserModel(temp.ID, temp.first_name, temp.last_name, temp.email, temp.type, temp.location, temp.phone_number, temp.department_id, temp.image)
                requestState.value = RequestState.SUCCESS
            }
        }
    }

    fun readDepartments() {
        requestState.value = RequestState.LOADING
        departmentViewModel.getDepartments()
        departmentViewModel.getDepartmentsState.observeForever {
            if (it == RequestState.SUCCESS){
                requestState.value = RequestState.SUCCESS
            }
        }
    }

    fun getUsersGivenDepartmentID() = userViewModel.userDepId.value!!

    fun readUsersGivenDepartmentID(departmentID: Int) {
        requestState.value = RequestState.LOADING
        userViewModel.readUsersGivenDepartmentID(departmentID)
        userViewModel.readUserState.observeForever {
            if (it == RequestState.SUCCESS){
                requestState.value = RequestState.SUCCESS
            }
        }
    }
}