package com.example.quizapp.ui

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.databinding.FragmentQuizStartBinding
import com.example.quizapp.models.item
import com.example.quizapp.models.quizViewModel


class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: quizViewModel
    private lateinit var questionText: TextView
    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[quizViewModel::class.java]
        binding = FragmentQuestionBinding.inflate(layoutInflater)
        initializeViews()
        initializeListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[quizViewModel::class.java]
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        viewModel.currentQuestion.observe(requireActivity()){(item, isLast)->
            binding.textView.text = item?.question
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
                    findNavController().navigate(R.id.action_questionFragment2_to_quizEndFragment2)
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
        questionText = binding.textView
        nextButton = binding.button2
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
}