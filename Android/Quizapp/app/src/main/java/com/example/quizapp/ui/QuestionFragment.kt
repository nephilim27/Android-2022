package com.example.quizapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.models.QuizViewModel
import com.google.android.material.snackbar.Snackbar


class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var questionText: TextView
    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentQuestionBinding.inflate(layoutInflater)
        initializeViews()
        initializeListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        viewModel.currentQuestion.observe(requireActivity()){(item, isLast)->
            binding.questionText.text = item?.question
            binding.radioButton.text = item?.answers?.get(0)
            binding.radioButton2.text = item?.answers?.get(1)
            binding.radioButton3.text = item?.answers?.get(2)
            binding.radioButton4.text = item?.answers?.get(3)
            if (isLast){
                nextButton.text = "SUBMIT"
                nextButton.setOnClickListener{
                    val checked = getChecked()

                    if(checked == -1) {
                        return@setOnClickListener
                    }

                    viewModel.checkAnswer(checked+1)
                    binding.radioGroup.clearCheck()
                    replaceFragment(QuizEndFragment())
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeListeners()
    }
    fun initializeViews(){
        questionText = binding.questionText
        nextButton = binding.nextButton
        radioGroup = binding.radioGroup
        radioButton1 = binding.radioButton
        radioButton2 = binding.radioButton2
        radioButton3 = binding.radioButton3
        radioButton4 = binding.radioButton4
    }

    fun getChecked(): Int {
        if(binding.radioButton.isChecked) {
            return 0
        }

        if(binding.radioButton2.isChecked) {
            return 1
        }

        if(binding.radioButton3.isChecked) {
            return 2
        }

        if(binding.radioButton4.isChecked) {
            return 3
        }

        return -1
    }

    fun initializeListeners(){
        nextButton.setOnClickListener{
            val checked = getChecked()
            Log.d("INFO", checked.toString())
            if (checked == -1){
                return@setOnClickListener
            }
            viewModel.checkAnswer(checked+1)
            binding.radioGroup.clearCheck()
            viewModel.getNextQuestion()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("QuestionFragment", "onAttach: ")
        val callback : OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val snack = view?.let { Snackbar.make(it, "You can't go back", Snackbar.LENGTH_SHORT) }
                if (snack != null) {
                    snack.show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}