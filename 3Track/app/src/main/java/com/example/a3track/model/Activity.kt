package com.example.a3track.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Activity (
    var ID: Int,
    var type: Int,
    var created_by_user_id: Int,
    var sub_type: Int,
    var created_time: Long,
    var sub_ID: Int,
    var sub_user_ID: Int,
    var note: String?,
    var progress: Int?
)
