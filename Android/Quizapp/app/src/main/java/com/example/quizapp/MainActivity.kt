package com.example.quizapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.models.Item
import com.example.quizapp.models.MyAdapter
import com.example.quizapp.models.Question
import com.example.quizapp.models.item
import com.example.quizapp.ui.*


@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newRecylerview: RecyclerView
    private lateinit var newArrayList: ArrayList<Question>
    private lateinit var tempArrayList: ArrayList<Question>
    lateinit var questions: Array<String>
    lateinit var items: Array<String>
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
        replaceFragment(HomeFragment())
        Log.i(TAG, "onCreate() called.")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
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

//        questions = arrayOf(
//            "Who developed Kotlin?",
//            "Which extension is responsible to save Kotlin files?",
//            "How to do a multi-line comment in Kotlin language?",
//            "The two types of constructors in Kotlin are?",
//            "What handles null exception in Kotlin?",
//            "The correct function to get the length of a string in Kotlin is?",
//            "The correct function to get the length of a string in Kotlin is?",
//            "In Kotlin the default visibility operator is?",
//            "What defines sealed class in Kotlin?",
//            "The functions in Kotlin can be divided in how many types?",
//            "Which are the basic data types in Kotlin?"
//        )
//
//        newRecylerview = findViewById(R.id.listOfQuestions)
//        newRecylerview.layoutManager = LinearLayoutManager(this)
//        newRecylerview.setHasFixedSize(true)
//
//
//        newArrayList = arrayListOf<Question>()
//        tempArrayList = arrayListOf<Question>()
//        getUserdata()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

//    private fun getUserdata() {
//
//        for (i in questions.indices) {
//
//            val item = Question(questions[i])
//            newArrayList.add(item)
//
//        }
//        newRecylerview.adapter = MyAdapter(newArrayList)
//    }
}