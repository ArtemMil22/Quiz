package com.example.myapplication

import androidx.lifecycle.ViewModel


class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex = 0
    var isCheater = false
    var answerGiven: Boolean = false


    val isAnswerGiven: Boolean
        get() = answerGiven

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun changeAnswerGivenState(state: Boolean) {
        answerGiven = state
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}

//init {
//    Log.d(TAG, "ViewModel instance created")
//}
//override fun onCleared() {
//    super.onCleared()
//    Log.d(TAG, "ViewModel instance about to be destroyed")
//}

//fun moveToPrev() {
//    //TODO Check if currentIndex is 0
//    currentIndex = if (currentIndex != 0) {
//        (currentIndex - 1) % questionBank.size
//    } else {
//        (questionBank.size - 1)
//    }
//}

//private const val TAG = "QuizViewModel"
