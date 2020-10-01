package com.example.quizadmin.di

import com.example.quizadmin.addquestion.QuestionUseCase
import com.example.quizadmin.addquestion.QuestionUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UsecaseModule{


@Binds
abstract fun bindQuestionUseCase(questionUseCaseImp: QuestionUseCaseImp):QuestionUseCase
}