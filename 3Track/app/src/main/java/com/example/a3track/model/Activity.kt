package com.example.a3track.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Activity (
    var ID: Int,
    var Type: Int,
    var User: Int,
    var SubType: Int,
    var Time: Long,
    var SubID: Int,
    var SubUserID: Int,
    var Note: String,
    var Progress: Int
)
