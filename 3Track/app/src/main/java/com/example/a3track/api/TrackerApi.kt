package com.example.a3track.api

import com.example.a3track.Util.Constants
import com.example.a3track.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TrackerApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST(Constants.CREATE_TASK)
    suspend fun createTask(@Body task: Task): Response<List<Task>>

    @GET(Constants.GET_USERS_URL)
    suspend fun getUsers(@Header("token") token: String): Response<List<User>>

    @GET(Constants.GET_MYUSER_URL)
    suspend fun getMyUser(@Header("token") token: String): Response<User>

    @GET(Constants.GET_TASKS_URL)
    suspend fun getTasks(@Header("token") token: String): Response<List<Task>>

    @GET(Constants.GET_ACTIVITIES_URL)
    suspend fun getActivities(@Header("token") token: String): Response<List<Activity>>

    companion object {
        fun getApi(): TrackerApi? {
            return ApiClient.client?.create(TrackerApi::class.java)
        }
    }

}