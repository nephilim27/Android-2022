package com.example.quizapp.ui

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.ItemList
import com.example.quizapp.databinding.FragmentQuestionAddBinding
import com.example.quizapp.models.Item
import com.example.quizapp.models.QuizViewModel
import kotlin.properties.Delegates


class QuestionAddFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentQuestionAddBinding
    private lateinit var placeHolder: EditText
    private lateinit var correctAnswer: EditText
    private lateinit var answer1: EditText
    private lateinit var answer2: EditText
    private lateinit var answer3: EditText
    private lateinit var answer4: EditText
    private lateinit var addButton: Button
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            initViewItems()
            registerListeners(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentQuestionAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun registerListeners(view: View){
        addButton = binding.addQuestionButton
        addButton.setOnClickListener {
            viewModel.items.add(
                Item(question = placeHolder.text.toString(), answers = mutableListOf(answer1.text.toString(),
                    answer2.text.toString(), answer3.text.toString(), answer4.text.toString()),
                    correct = correctAnswer.text.toString().toInt())
            )

            viewModel.itemlist.add(
                ItemList(question = placeHolder.text.toString())
            )

            placeHolder.text.clear()
            correctAnswer.text.clear()
            answer1.text.clear()
            answer2.text.clear()
            answer3.text.clear()
            answer4.text.clear()

        }
    }

    private fun initViewItems() {
        placeHolder = binding.placeholder
        correctAnswer = binding.correctAnswer
        answer1 = binding.answer1
        answer2 = binding.answer2
        answer3 = binding.answer3
        answer4 = binding.answer4
        addButton = binding.addQuestionButton
    }
}