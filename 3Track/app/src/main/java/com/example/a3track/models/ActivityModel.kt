package com.example.a3track.models

data class ActivityModel(
    var id: Int,
    var activityType: Int,
    var activitySubType: Int,
    var createdByUser: UserModel?,
    var createdTime: Long,
    var activityTypeSubId: Int,
    var assignedToId: UserModel?,
    var note: String?,
    var progress: Int?
)
