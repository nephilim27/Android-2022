package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentDetailBinding
import com.example.quizapp.databinding.FragmentQuestionAddBinding
import com.example.quizapp.models.QuizViewModel


class DetailFragment : Fragment() {

    lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentDetailBinding
    lateinit var correctNumber: TextView
    lateinit var answ1: TextView
    lateinit var answ2: TextView
    lateinit var answ3: TextView
    lateinit var answ4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()

        val position = viewModel.position

        val list = viewModel.items

        correctNumber.text = list[position].correct.toString()
        answ1.text = list[position].answers[0]
        answ2.text = list[position].answers[1]
        answ3.text = list[position].answers[2]
        answ4.text = list[position].answers[3]

    }

    private fun initViewItems(){
        correctNumber = binding.correctNumber
        answ1 = binding.answ1
        answ2 = binding.answ2
        answ3 = binding.answ3
        answ4 = binding.answ4
    }
}