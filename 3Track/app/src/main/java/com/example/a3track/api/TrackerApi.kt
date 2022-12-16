package com.example.a3track.api

import android.widget.TextView
import com.example.a3track.Util.Constants
import com.example.a3track.model.LoginRequest
import com.example.a3track.model.LoginResponse
import com.example.a3track.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TrackerApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET(Constants.GET_USERS_URL)
    suspend fun getUsers(@Header("token") token: String): Response<List<User>>

}