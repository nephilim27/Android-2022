package com.example.a3track.viewModel

import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.a3track.MyApplication

class ProfileViewModel : ViewModel() {
    var email: String = MyApplication.email
    var phone: String = ""
    var location: String = ""
}