package com.example.quizadmin.addquestion

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.quizadmin.Quiz
import com.example.quizadmin.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_question.*

@AndroidEntryPoint
class AddQuestionFragment : Fragment() {

    private val viewmdel: AddQuestionViewModel by viewModels()

    companion object {
        private const val TAG = "AddQuestionFrag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmdel.createQuestionLiveData.observe(requireActivity(), Observer {
            Toast.makeText(
                requireContext(),
                "Question Added Successfully with ID :: $it",
                Toast.LENGTH_SHORT

            ).show()
            Log.d(TAG, "QuestionAd<><> :: $it")
            showSuccessAlert()
        })

        bt_verify_upload.setOnClickListener {

            addQuestion()
        }
    }

    private fun showSuccessAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Success")
        builder.setMessage("Question Added Successfully")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Ok") { dialogInterface, which ->
            Navigation.findNavController(requireActivity(), R.id.frameContainer)
                .navigate(R.id.addquestion_to_viewquestion)
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun addQuestion() {
        val question: String = tet_question_addquestionfrag.getText().toString().trim({ it <= ' ' })
        val option1: String = tet_option1.getText().toString().trim({ it <= ' ' })
        val option2: String = tet_option2.getText().toString().trim({ it <= ' ' })
        val option3: String = tet_option3.getText().toString().trim({ it <= ' ' })
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId

        til_question_addquestionfrag.error = null
        til_option1_addquestionfrag.error = null
        til_option2_addquestionfrag.error = null
        til_option3_addquestionfrag.error = null
        if (TextUtils.isEmpty(question)) {
            til_question_addquestionfrag.error = resources.getString(R.string.enter_question)
        } else if (TextUtils.isEmpty(option1)) {
            til_option1_addquestionfrag.error = resources.getString(R.string.enter_option1)
        } else if (TextUtils.isEmpty(option2)) {
            til_option2_addquestionfrag.error = resources.getString(R.string.enter_option2)
        } else if (TextUtils.isEmpty(option3)) {
            til_option3_addquestionfrag.error = resources.getString(R.string.enter_option3)
        } else if (selectedOption == -1) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.enter_correct_answer),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val quiz = Quiz(
                question = question,
                option1 = option1,
                option2 = option2,
                option3 = option3,
                correctans = selectedOption
            )
            viewmdel.addQuestion(quiz)
        }
    }
}