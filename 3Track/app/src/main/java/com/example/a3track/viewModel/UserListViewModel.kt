package com.example.a3track.viewModel

import android.app.DownloadManager.Request
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.Util.RequestState
import com.example.a3track.model.UpdateProfile
import com.example.a3track.model.User
import com.example.a3track.models.UserModel
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

    var userList: MutableLiveData<List<UserModel>> = MutableLiveData()
    var userDepId: MutableLiveData<List<UserModel>> = MutableLiveData()
    val readUserState: MutableLiveData<RequestState> = MutableLiveData()
    val updateProfileState: MutableLiveData<RequestState> = MutableLiveData()
    private var currentUser: User? = null

    fun getMyUser() {
        readUserState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                val response = repository.getMyUser(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        currentUser = response.body()
                        Log.d("MyUser", "getMyUser: " + currentUser.toString())
                        readUserState.value = RequestState.SUCCESS
                        Log.d("GlobalViewModel", "user: SUCCESS")
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

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun readUsersGivenDepartmentID(departmentID: Int) {
        readUserState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                userList.value = listOf()
                userDepId.value = listOf()
                val response = repository.getUsers(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        val users = response.body()!!
                        for (user in users) {
                            userList.value = userList.value?.plus(
                                UserModel(
                                    user.ID,
                                    user.last_name,
                                    user.first_name,
                                    user.email,
                                    user.type,
                                    user.location,
                                    user.phone_number,
                                    user.department_id,
                                    user.image
                                )
                            )
                        }
                        Log.d("UserList", "getUsers: " + userList.value.toString())
                        userDepId.value = userList.value?.filter { it.department_id == departmentID }
                        Log.d("UserListFiltered", "getUsers: " + userDepId.value.toString())
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

    fun readUsers() {
        readUserState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                userList.value = listOf()
                val response = repository.getUsers(MyApplication.token)
                if (response != null) {
                    if(response.isSuccessful) {
                        val users = response.body()!!
                        for (user in users) {
                            userList.value = userList.value?.plus(
                                UserModel(
                                    user.ID,
                                    user.last_name,
                                    user.first_name,
                                    user.email,
                                    user.type,
                                    user.location,
                                    user.phone_number,
                                    user.department_id,
                                    user.image
                                )
                            )
                        }
                        Log.d("UserList", "getUsers: " + userList.value.toString())
                        readUserState.value = RequestState.SUCCESS
                        Log.d("GlobalViewModel", "user: SUCCESS")
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

    fun updateProfile(updateProfileRequest: UpdateProfile) {
        updateProfileState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(MyApplication.token, updateProfileRequest)
                if (response != null) {
                    if(response.isSuccessful) {
                        updateProfileState.value = RequestState.SUCCESS
                        Log.d("GlobalViewModel", "user: SUCCESS")
                    } else{
                        Log.i("xxx-uvm", response.message())
                        updateProfileState.value = RequestState.UNKNOWN_ERROR
                    }
                }
            } catch (e: Exception) {
                Log.i("ajaj", e.toString())
                updateProfileState.value = RequestState.UNKNOWN_ERROR

            }
        }
    }
}