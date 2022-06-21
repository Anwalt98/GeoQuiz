package com.example.geoquiz

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.geoquiz.databinding.ActivityMainBinding

private const val KEY_INDEX = "index"
private const val KEY_INCORRECT_COUNTER = "IC"
private const val KEY_CORRECT_COUNTER = "CC"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding




    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        val correctCounter = savedInstanceState?.getInt(KEY_CORRECT_COUNTER, 0) ?: 0
        val incorrectCounter = savedInstanceState?.getInt(KEY_INCORRECT_COUNTER, 0) ?: 0
        quizViewModel.correctCounter = correctCounter
        quizViewModel.incorrectCounter = incorrectCounter
        quizViewModel.currentIndex = currentIndex
        val nextQuestionText = quizViewModel.questionBank[quizViewModel.currentIndex].textResId
        binding.questionTV.setText(nextQuestionText)
        updateCounters()
        checkVisibility()
        
        //слушатель для TRUE
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            quizViewModel.moveToNext()
            updateQuestion()
            checkVisibility()
        }

        // слушатель для False
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            quizViewModel.moveToNext()
            updateQuestion()
            checkVisibility()
        }
        binding.backButton.setOnClickListener {
            quizViewModel.moveBack()
            updateQuestion()
            checkVisibility()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            checkVisibility()
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_INCORRECT_COUNTER, quizViewModel.incorrectCounter)
        savedInstanceState.putInt(KEY_CORRECT_COUNTER, quizViewModel.correctCounter)
    }


    private fun updateQuestion() {
        val nextQuestionText = quizViewModel.currentQuestionText
        binding.questionTV.setText(nextQuestionText)
    }

    private fun showCorrectToast() {
        Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show()
    }

    private fun showIncorrectToast() {
        Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show()
    }

    private fun checkVisibility() {
        if (quizViewModel.currentIndex == 0) binding.backButton.visibility = View.INVISIBLE else binding.backButton.visibility = View.VISIBLE
    }


    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (correctAnswer == userAnswer) {
           quizViewModel.updateCounter(true)
            binding.correctTV.text = "Correct answers: ${quizViewModel.correctCounter}"
            showCorrectToast()
        } else {
            showIncorrectToast()
            quizViewModel.updateCounter(false)
            binding.incorrectTV.text = "Incorrect answers: ${quizViewModel.incorrectCounter}"
        }
    }
    fun updateCounters()
    {
        binding.correctTV.text = "Correct answers: ${quizViewModel.correctCounter}"
        binding.incorrectTV.text = "Incorrect answers: ${quizViewModel.incorrectCounter}"
    }
}
