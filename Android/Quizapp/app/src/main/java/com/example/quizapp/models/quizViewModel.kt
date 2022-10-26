package com.example.quizapp.models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class quizViewModel : ViewModel() {
    private val nrOfQuestions = 5
    private var questions = selectRandomItems(nrOfQuestions)
    var currentQuestion: MutableLiveData<Pair<Item?, Boolean>> = MutableLiveData<Pair<Item?, Boolean>>()
    var questionIterator: MutableIterator<Item> = startQuiz().iterator()
    private var correctAnswers = 0

    init {
        getNextQuestion()
    }

    fun selectRandomItems (nr: Int): MutableList<Item>{
        val temp = com.example.quizapp.models.item
        val randomItems = mutableListOf<Item>()
        var number = nr
        if (nr > temp.size){
            number = temp.size
        }
        if (number <= 0){
            println("The number can't be 0 or less")
            return randomItems
        }
        while(randomItems.size != number) {
            val randQuestion = temp.random()
            if (!randomItems.contains(randQuestion)) {
                randomItems.add(randQuestion)
            }
        }
        return randomItems
    }

    fun getNextQuestion(){
        if (questionIterator.hasNext()){
            currentQuestion.value = questionIterator.next() to !questionIterator.hasNext()
        }
    }

    fun checkAnswer(answer: Int){
        if (answer == currentQuestion.value?.first?.correct){
            correctAnswers++
        }
    }

    fun getCorrectAnswers(): Int{
        return correctAnswers
    }

    fun getNumberOfQuestions(): Int{
        return questions.size
    }

    fun startQuiz(): MutableList<Item>{
        return questions
    }
}

