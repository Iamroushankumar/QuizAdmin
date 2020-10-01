package com.example.quizadmin.addquestion

import com.example.quizadmin.ObjectBox
import com.example.quizadmin.Quiz
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class QuestionUseCaseImp @Inject constructor(
    private val database: Box<Quiz>,

    ):QuestionUseCase {


    override fun createLocal(question: Quiz): Single<String> =
        Single.create<String> { val id=database.put(question)
        it.onSuccess(id.toString())}

}