package com.example.quizadmin.addquestion

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.quizadmin.Quiz
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddQuestionViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
    , private val questionUseCase: QuestionUseCase
):ViewModel(), CoroutineScope
{

    val disposable = CompositeDisposable()
    val createQuestionLiveData = MutableLiveData<String>()
    private var job = Job()


    fun addQuestion(quiz: Quiz) {
        launch {
            disposable.add(
                questionUseCase.createLocal(quiz).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        createQuestionLiveData.value = t
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}