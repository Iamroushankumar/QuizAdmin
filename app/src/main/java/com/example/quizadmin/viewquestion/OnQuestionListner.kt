package com.example.quizadmin.viewquestion

import com.example.quizadmin.Quiz

interface OnQuestionListner {
    fun onQuestionDelete(id:Long)
    fun onQuestionEdit(quiz: Quiz)
    fun onQuestionSelect(id:Long)
}