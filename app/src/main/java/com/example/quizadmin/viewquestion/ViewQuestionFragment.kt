package com.example.quizadmin.viewquestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizadmin.Quiz
import com.example.quizadmin.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_view_question.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ViewQuestionFragment : Fragment(), OnQuestionListner {
    companion object {
        val TAG = ViewQuestionFragment::class.simpleName
    }

    private val viewModel: ViewQuestionViewModel by viewModels()
    private var list = mutableListOf<Quiz>()

    private val adapter: ViewQuestionAdapter by lazy {
        viewModel.allquestionsLiveData.observe(requireActivity(), Observer { it ->
            list.clear()
            list.addAll(it)
            adapter.update(list)
        })
        ViewQuestionAdapter(list, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_viewquestions.layoutManager = LinearLayoutManager(context)

        rv_viewquestions.adapter = adapter
        viewModel.getQuestion()

        viewModel.deletequestionLivedata.observe(requireActivity(), Observer { it ->
            if (it) {
                Toast.makeText(requireContext(), "Data Delete Successfully", Toast.LENGTH_SHORT)
                    .show()
                viewModel.getQuestion()

            } else Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show()

        })
    }

    override fun onQuestionDelete(id: Long) {
        viewModel.deleteLocal(id)
    }

    override fun onQuestionEdit(id: Long) {
    }

    override fun onQuestionSelect(id: Long) {
    }


}