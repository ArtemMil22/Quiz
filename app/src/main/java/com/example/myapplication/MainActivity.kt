package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


private const val REQUEST_CODE_CHEAT = 0
private const val KEY_INDEX = "index"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        val currentIndex = savedInstanceState
            ?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        updateQuestion()

        cheatButton.setOnClickListener { view ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(
                this@MainActivity, answerIsTrue
            )
            val options = ActivityOptions
                .makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
        }

        trueButton.setOnClickListener {
            if (!quizViewModel.isAnswerGiven) {
                quizViewModel.changeAnswerGivenState(true)
                checkAnswer(true)
            }
        }

        falseButton.setOnClickListener {
            if (!quizViewModel.isAnswerGiven) {
                quizViewModel.changeAnswerGivenState(true)
                checkAnswer(false)
            }
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(
                    EXTRA_ANSWER_SHOWN,
                    false
                ) ?: false
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun updateQuestion() {
        quizViewModel.changeAnswerGivenState(false)
        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

}

//val intent = Intent(this,CheatActivity::class.java)

//        var messageResId: Int = 0
//        messageResId = if(userAnswer == quizViewModel.currentQuestionAnswer) {
//            R.string.correct_toast
//        } else {
//            R.string.incorrect_toast
//        }

//private lateinit var prevButton: Button

//        prevButton.setOnClickListener {view: View ->
//            quizViewModel.moveToPrev()
//            updateQuestion()
//        }

//override fun onStart() {
//    super.onStart()
//    Log.d(TAG, "onStart() called")
//}
//
//override fun onResume() {
//    super.onResume()
//    Log.d(TAG, "onResume() called")
//}
//
//override fun onPause() {
//    super.onPause()
//    Log.d(TAG, "OnPause() called")
//}
//
//override fun onStop() {
//    super.onStop()
//    Log.d(TAG, "onStop() called")
//}
//
//override fun onDestroy() {
//    super.onDestroy()
//    Log.d(TAG, "onDestroy() called")
//}

//        Log.d(TAG, "Updating question text", Exception())
//private const val TAG = "MainActivity"
//Log.i(TAG, "onSaveInstanceState")
//
//Log.d(TAG, "onCreate(Bundle? called")

