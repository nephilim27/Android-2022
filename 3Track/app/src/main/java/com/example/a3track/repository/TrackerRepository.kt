package com.example.a3track.repository

import com.example.a3track.MyApplication
import com.example.a3track.api.RetrofitInstance
import com.example.a3track.api.TrackerApi
import com.example.a3track.model.*
import retrofit2.Response

class TrackerRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.api.login(request)
    }

    suspend fun createTask(task: Task): Response <List<Task>>? {
        return TrackerApi.getApi()?.createTask(task)
    }

    suspend fun getTasks(token: String): Response<List<Task>>? {
        return  TrackerApi.getApi()?.getTasks(token)
    }

    suspend fun getUsers(token: String): Response<List<User>>? {
        return TrackerApi.getApi()?.getUsers(token)
    }

    suspend fun getMyUser(token: String): Response<User>? {
        return TrackerApi.getApi()?.getMyUser(token)
    }

    suspend fun getActivities(token: String): Response<List<Activity>>? {
        return  TrackerApi.getApi()?.getActivities(token)
    }

}