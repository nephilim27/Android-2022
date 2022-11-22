package com.example.quizapp.ui

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizStartBinding
import com.example.quizapp.models.QuizViewModel
import com.google.android.material.snackbar.Snackbar
import android.content.SharedPreferences
import android.preference.PreferenceManager


class QuizStartFragment : Fragment() {
    private lateinit var binding: FragmentQuizStartBinding
    private lateinit var userName: EditText
    private lateinit var startButton: Button
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentQuizStartBinding.inflate(inflater, container, false)
        viewModel.resetQuiz()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems()
            registerListeners(view)
        }
    }

    private fun registerListeners(view: View) {
        startButton = binding.startButton
        Log.d("userName", userName.text.toString())
        startButton.setOnClickListener {
            if (userName.text.isEmpty()){
                val snack = Snackbar.make(view, "Please enter your name", Snackbar.LENGTH_SHORT)
                snack.show()
            }
            else {
                replaceFragment(QuestionFragment())
            }
            viewModel.userName = userName.text.toString()
        }
    }


    private fun initViewItems() {
        userName = binding.userName
        startButton = binding.startButton
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}