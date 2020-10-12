package com.example.quizadmin.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.quizadmin.Quiz
import com.example.quizadmin.addquestion.QuestionUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ViewQuestionViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val questionUseCase: QuestionUseCase
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val allquestionsLiveData = MutableLiveData<List<Quiz>>()
    val deletequestionLivedata=MutableLiveData<Boolean>()
    val disposable = CompositeDisposable()
    private var job = Job()

    fun getQuestion() {
        launch {
            disposable.add(
                questionUseCase.getQuestion().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ it ->
                        allquestionsLiveData.value = it
                    }, { error ->
                        error.printStackTrace()
                    })
            )

        }
    }

    fun deleteLocal(id:Long){
        launch {
            disposable.add(
                questionUseCase.deleteQuestion(id).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe({it->
                    deletequestionLivedata.value=it
                },{error->error.printStackTrace()})
            )
        }
    }

}