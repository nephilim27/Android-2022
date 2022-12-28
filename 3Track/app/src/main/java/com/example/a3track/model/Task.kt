package com.example.a3track.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Task (
    var ID: Int,
    var title: String,
    var description: String,
    var time: Int,
    var assigner: Int,
    var assignee: Int,
    var priority: Int,
    var deadline: Int,
    var departmentID: Int,
    var status: Int,
    var percentage: Int)


//val taskList: MutableList<Task> = mutableListOf(
//    Task(project = "HR Project", title = "Set-up company profile", assigner = "Janette Doe", time = "3:45 pm", assignee = "James Doe", deadline = "June 01 2023", priority = "High prio", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate...", status = "New", percentage = "10%"),
//    Task(project = "Dev Project", title = "Onboarding for James Doe", assigner = "Johnny Doe", time = "2:45 pm", assignee = "James Doe", deadline = "June 01 2023", priority = "High prio", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate...", status = "New", percentage = "10%"),
//    Task(project = "Dev Project", title = "Onboarding for James Doe", assigner = "Johnny Doe", time = "2:45 pm", assignee = "James Doe", deadline = "June 01 2023", priority = "High prio", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate...", status = "New", percentage = "10%"),
//    Task(project = "Dev Project", title = "Onboarding for James Doe", assigner = "Johnny Doe", time = "2:45 pm", assignee = "James Doe", deadline = "June 01 2023", priority = "High prio", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate...", status = "New", percentage = "10%"),
//    Task(project = "Dev Project", title = "Onboarding for James Doe", assigner = "Johnny Doe", time = "2:45 pm", assignee = "James Doe", deadline = "June 01 2023", priority = "High prio", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate...", status = "New", percentage = "10%")
//)


