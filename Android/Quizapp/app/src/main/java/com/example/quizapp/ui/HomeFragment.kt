package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.models.QuizViewModel

@Suppress("UNUSED_EXPRESSION")
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var testSkill: Button
    private lateinit var readQuestion: Button
    private lateinit var createQuestion: Button
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems()
            registerListeners()
        }
    }

    private fun registerListeners() {
        testSkill = binding.testSkillsButton
        readQuestion = binding.readQuestionsButton
        createQuestion = binding.createQuestionButton

        testSkill.setOnClickListener{
            replaceFragment(QuizStartFragment())
        }

        readQuestion.setOnClickListener {
            replaceFragment(QuestionListFragment())
        }

        createQuestion.setOnClickListener {
            replaceFragment(QuestionAddFragment())
        }
    }

    private fun initViewItems() {
        testSkill = binding.testSkillsButton
        readQuestion = binding.readQuestionsButton
        createQuestion = binding.createQuestionButton
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}













