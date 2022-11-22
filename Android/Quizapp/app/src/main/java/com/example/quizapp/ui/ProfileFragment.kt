package com.example.quizapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.databinding.FragmentProfileBinding
import com.example.quizapp.models.QuizViewModel

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: QuizViewModel
    lateinit var playerName: TextView
    private lateinit var playerNameText: TextView
    private lateinit var highScore: TextView
    private lateinit var highScoreText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems()
            registerListeners()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun registerListeners(){
        playerNameText.text = viewModel.userName
        highScoreText.text = viewModel.highscore.toString()

    }

    private fun initViewItems(){
        playerName = binding.playerName
        playerNameText = binding.nameText
        highScore = binding.highScore
        highScoreText = binding.highScoreText
    }
}