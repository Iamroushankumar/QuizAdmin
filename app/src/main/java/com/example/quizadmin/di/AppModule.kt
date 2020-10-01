package com.example.quizadmin.di

import android.content.Context
import com.example.quizadmin.ObjectBox
import com.example.quizadmin.Quiz
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideQuestionBox(
        @ApplicationContext context: Context,
    ): Box<Quiz> {
      return  ObjectBox.boxStore.boxFor(Quiz::class)

    }
}