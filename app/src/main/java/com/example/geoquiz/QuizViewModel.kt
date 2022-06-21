package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_volkhov, true)
    )
    var currentIndex = 0

    val currentQuestionAnswer: Boolean get() =  questionBank[currentIndex].answer

    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    var correctCounter = 0

    var incorrectCounter = 0

    fun moveToNext() {
        currentIndex = (currentIndex + 1) %
                questionBank.size
    }
    fun moveBack(){
        currentIndex --
    }
    fun updateCounter(boolean : Boolean){
        if (boolean) correctCounter++ else incorrectCounter++
    }
    }
