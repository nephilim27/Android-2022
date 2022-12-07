package com.example.a3track

import android.app.Application

class MyApplication : Application() {
    companion object {
        var token: String = ""
        var deadline: Long = 0L
        var email: String = ""
    }
}