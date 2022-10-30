package com.example.quizapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.models.item
import com.example.quizapp.ui.*


@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    val TAG = "MainActivity"

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
        //replaceFragment(HomeFragment())
        Log.i(TAG, "onCreate() called.")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.quizTime -> replaceFragment(QuizStartFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.listOfQuestions -> replaceFragment(QuestionListFragment())
                R.id.newQuestion -> replaceFragment(QuestionAddFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}