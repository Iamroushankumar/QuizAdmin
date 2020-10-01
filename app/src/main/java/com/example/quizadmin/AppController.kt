package com.example.quizadmin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.BoxStore

@HiltAndroidApp
class AppController: Application(){
    companion object Constants {
        const val TAG = "ObjectBoxExample"
    }
    lateinit var boxstore:BoxStore;
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}