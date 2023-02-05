package com.example.a3track.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3track.MyApplication
import com.example.a3track.Util.RequestState
import com.example.a3track.models.DepartmentModel
import com.example.a3track.repository.TrackerRepository
import kotlinx.coroutines.launch

class DepartmentViewModelFactory(private val repository: TrackerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DepartmentViewModel(repository) as T
    }
}

class DepartmentViewModel(private val repository: TrackerRepository): ViewModel() {
    val departmentList: MutableLiveData<List<DepartmentModel>> = MutableLiveData()
    val getDepartmentsState: MutableLiveData<RequestState> = MutableLiveData()

    fun getDepartments() {
        getDepartmentsState.value = RequestState.LOADING
        viewModelScope.launch {
            try {
                val response = repository.getDepartments(MyApplication.token)
                if(response.isSuccessful) {
                    departmentList.value = listOf()
                    val departmentListResponse = response.body()!!
                    for (department in departmentListResponse) {
                        departmentList.value = departmentList.value?.plus(DepartmentModel(department.ID, department.name))
                    }
                    getDepartmentsState.value = RequestState.SUCCESS
                } else{
                    getDepartmentsState.value = RequestState.UNKNOWN_ERROR
                }
            }  catch (e: Exception) {
                getDepartmentsState.value = RequestState.UNKNOWN_ERROR
            }
        }
    }

}