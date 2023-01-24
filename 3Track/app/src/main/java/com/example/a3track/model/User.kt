package com.example.a3track.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var ID: Int,
    var last_name: String,
    var first_name: String,
    var email: String,
    var type: Int,
    var location: String,
    var phone_number: String,
    var department_id: Int
)