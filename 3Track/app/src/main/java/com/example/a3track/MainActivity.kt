package com.example.a3track

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.a3track.databinding.ActivityMainBinding
import com.example.a3track.fragment.*
import com.example.a3track.model.LoginResult
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart() called.")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume() called.")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart() called.")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause() called.")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop() called.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy() called.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(LoginFragment())

        // Read data from preferences
        val prefs = this.getPreferences(MODE_PRIVATE)
        val token = prefs.getString("token", "")
        val deadline = prefs.getLong("deadline", 0L)


        Log.i("token", "token: " + token)
        // @TODO - check the deadline validity
        var isValid = false
        if(deadline != 0L) {
            isValid = true
        }

        if (!token.equals("") && isValid ) {
            MyApplication.token = token!!
            MyApplication.email = prefs.getString("email","")!!
            replaceFragment(ActivitiesFragment())
        }


        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.activities -> replaceFragment(ActivitiesFragment())
                R.id.tasks -> replaceFragment(MyTasksFragment())
                R.id.myGroups -> replaceFragment(MyGroupsFragment())
                R.id.settings -> replaceFragment(SettingsFragment())

                else -> {

                }

            }
            true
        }

        Log.i(TAG, "onCreateMain() called.")
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}