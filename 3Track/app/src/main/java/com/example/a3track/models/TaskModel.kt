package com.example.a3track.models

import com.example.a3track.model.Department

data class TaskModel(var ID: Int,
                     var title: String,
                     var description: String,
                     var created_time: Long,
                     var created_by_user_ID: UserModel?,
                     var assigned_to_user_ID: UserModel?,
                     var priority: Int,
                     var deadline: Long,
                     var departmentID: DepartmentModel?,
                     var status: Int,
                     var percentage: Int?)
