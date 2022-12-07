package com.example.a3track

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.a3track.databinding.ActivityMainBinding
import com.example.a3track.fragment.LoginFragment

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
//        val controller = findNavController(R.id.loginFragment)

        Log.i("xxx", "token: " + token)
        // @TODO - check the token's validity
        val isValid = true
        if (!token.equals("") && isValid ) {
            MyApplication.token = token!!
            MyApplication.email = prefs.getString("email","")!!

//            controller.navigate(R.id.userListFragment)
        }

        Log.i(TAG, "onCreateMain() called.")
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}