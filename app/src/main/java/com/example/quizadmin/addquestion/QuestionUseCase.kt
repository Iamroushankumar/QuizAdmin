package com.example.quizadmin.addquestion

import com.example.quizadmin.Quiz
import io.reactivex.rxjava3.core.Single

interface QuestionUseCase {
    fun createLocal(employee: Quiz): Single<String>
    fun getQuestion(): Single<List<Quiz>>
    fun deleteQuestion(id: Long):Single<Boolean>
    fun editQuestion(quiz:Quiz,id: Long):Single<String>

    fun getData(id: Long):Single<Quiz>
}