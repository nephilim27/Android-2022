package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizEndBinding
import com.example.quizapp.models.quizViewModel
import com.google.android.material.snackbar.Snackbar


class QuizEndFragment : Fragment() {

    private lateinit var viewModel: quizViewModel
    private lateinit var binding: FragmentQuizEndBinding
    private lateinit var scoreText: TextView
    private lateinit var tryAgainButton : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(quizViewModel::class.java)
        binding = FragmentQuizEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners(view)
        scoreText = binding.textView4
        scoreText.text = "${viewModel.getCorrectAnswers()} / ${viewModel.getNumberOfQuestions()} POINTS"
    }

    private fun initListeners(view: View) {
        tryAgainButton = binding.endButton
        tryAgainButton.setOnClickListener {
            val snack = Snackbar.make(view, "Could not implement this one yet", Snackbar.LENGTH_SHORT)
            snack.show()
        }
    }

}