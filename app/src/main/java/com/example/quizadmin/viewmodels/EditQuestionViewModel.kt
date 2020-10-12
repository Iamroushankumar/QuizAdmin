package com.example.quizadmin.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.quizadmin.Quiz
import com.example.quizadmin.addquestion.QuestionUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditQuestionViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
    , private val questionUseCase: QuestionUseCase
) :ViewModel() , CoroutineScope {
    val disposable = CompositeDisposable()
    private var job = Job()
    val editQuestionLiveData = MutableLiveData<String>()
    val questionLiveData = MutableLiveData<Quiz>()

    fun updateQuestion(quiz: Quiz,id:Long){
        launch{
            disposable.add(
                questionUseCase.editQuestion(quiz,id).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        editQuestionLiveData.value=t
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
    }
    fun getData(id:Long){
        launch{
            disposable.add(
                questionUseCase.getData(id).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        questionLiveData.value=t
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
    }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}