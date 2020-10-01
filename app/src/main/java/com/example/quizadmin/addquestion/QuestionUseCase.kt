package com.example.quizadmin.addquestion

import com.example.quizadmin.Quiz
import io.reactivex.rxjava3.core.Single

interface QuestionUseCase {
    fun createLocal(employee: Quiz): Single<String>

}