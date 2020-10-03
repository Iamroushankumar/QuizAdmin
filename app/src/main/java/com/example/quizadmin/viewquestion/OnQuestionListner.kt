package com.example.quizadmin.viewquestion

interface OnQuestionListner {
    fun onQuestionDelete(id:Long)
    fun onQuestionEdit(id: Long)
    fun onQuestionSelect(id:Long)
}