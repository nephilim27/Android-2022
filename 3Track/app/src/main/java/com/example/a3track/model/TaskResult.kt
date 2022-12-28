package com.example.a3track.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskResponse (
    var project: String,
    var title: String,
    var assigner: String,
    var time: String,
    var assignee: String,
    var deadline: String,
    var priority: String,
    var description: String,
    var status: String,
    var percentage: String
)