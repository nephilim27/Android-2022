package com.example.a3track.models

data class TaskModel(var ID: Int,
                     var title: String,
                     var description: String,
                     var time: Int,
                     var assigner: UserModel,
                     var assignee: UserModel,
                     var priority: Int,
                     var deadline: Long,
                     var departmentID: Int,
                     var status: Int,
                     var percentage: Int)
