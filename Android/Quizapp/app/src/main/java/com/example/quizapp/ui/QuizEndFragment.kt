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
import com.example.quizapp.models.QuizViewModel
import com.google.android.material.snackbar.Snackbar


class QuizEndFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: FragmentQuizEndBinding
    private lateinit var scoreText: TextView
    private lateinit var endText: TextView
    private lateinit var tryAgainButton : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)
        binding = FragmentQuizEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners(view)
        scoreText = binding.scoreText
        scoreText.text = "${viewModel.getCorrectAnswers()} / ${viewModel.getNumberOfQuestions()} POINTS"
    }

    private fun initListeners(view: View) {
        tryAgainButton = binding.endButton
        endText = binding.endText
        endText.setText("\tQUIZ RESULT \n Congratulations!")
        tryAgainButton.setOnClickListener {
            replaceFragment(QuizStartFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}