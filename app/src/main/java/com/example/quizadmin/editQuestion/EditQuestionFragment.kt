package com.example.quizadmin.editQuestion

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.quizadmin.Quiz
import com.example.quizadmin.R
import com.example.quizadmin.addquestion.AddQuestionFragment
import com.example.quizadmin.utils.Helper
import com.example.quizadmin.viewmodels.AddQuestionViewModel
import com.example.quizadmin.viewmodels.EditQuestionViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_question.*
import kotlinx.android.synthetic.main.fragment_edit_question.*
import kotlinx.android.synthetic.main.fragment_edit_question.iv_upload
import kotlinx.android.synthetic.main.fragment_edit_question.radioGroup
import kotlinx.android.synthetic.main.fragment_edit_question.tet_option1
import kotlinx.android.synthetic.main.fragment_edit_question.tet_option2
import kotlinx.android.synthetic.main.fragment_edit_question.tet_option3

@AndroidEntryPoint

class EditQuestionFragment : Fragment() {

    var compressedImageFile: Bitmap? = null
    private val viewmdel: EditQuestionViewModel by viewModels()

    lateinit var quizData: Quiz

    companion object {
        private const val TAG = "EditQuestionFrag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      val  quizid = arguments?.getLong("editData")
        if (quizid != null) {
            viewmdel.getData(quizid)
        }


        bt_edit.setOnClickListener {
            if(quizData!=null){
                EditQuestion()

            }
        }
        viewmdel.editQuestionLiveData.observe(requireActivity(), Observer {
            Toast.makeText(
                requireContext(),
                "Question Updated Successfully with ID :: $it",
                Toast.LENGTH_SHORT

            ).show()
            Log.d(EditQuestionFragment.TAG, "QuestionEdt<><> :: $it")
            showSuccessAlert()
        })
        viewmdel.questionLiveData.observe(requireActivity(), Observer {
            quizData=it
            populateView(quizData)


        })
        iv_upload.setOnClickListener({
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(), this)
        })
    }
    private fun showSuccessAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Success")
        builder.setMessage("Question Updated Successfully")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Ok") { dialogInterface, which ->
            Navigation.findNavController(requireActivity(), R.id.frameContainer)
                .popBackStack()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
    private fun EditQuestion() {
        val question: String =
            tet_question_editquestionfrag.getText().toString().trim({ it <= ' ' })
        val option1: String = tet_option1.getText().toString().trim({ it <= ' ' })
        val option2: String = tet_option2.getText().toString().trim({ it <= ' ' })
        val option3: String = tet_option3.getText().toString().trim({ it <= ' ' })
        val radioButtonID: Int = radioGroup.getCheckedRadioButtonId()
        val radioButton: View = radioGroup.findViewById(radioButtonID)
        val idx: Int = radioGroup.indexOfChild(radioButton)

        val image = Helper.convertImagetoBytes(compressedImageFile)
//val bitmap:Bitmap=iv_upload.getCroppedImage()
        til_question_editquestionfrag.error = null
        til_option1_editquestionfrag.error = null
        til_option2_editquestionfrag.error = null
        til_option3_editquestionfrag.error = null
        if (TextUtils.isEmpty(question)) {
            til_question_editquestionfrag.error = resources.getString(R.string.enter_question)
        } else if (TextUtils.isEmpty(option1)) {
            til_option1_editquestionfrag.error = resources.getString(R.string.enter_option1)
        } else if (TextUtils.isEmpty(option2)) {
            til_option2_editquestionfrag.error = resources.getString(R.string.enter_option2)
        } else if (TextUtils.isEmpty(option3)) {
            til_option3_editquestionfrag.error = resources.getString(R.string.enter_option3)
        } else if (idx == -1) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.enter_correct_answer),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            quizData.question=question
                quizData. option1 = option1
                quizData.  option2 = option2
                quizData. option3 = option3
                quizData. correctans = idx
            if(compressedImageFile!=null){
                quizData.image = image
            }
            viewmdel.updateQuestion(quizData, quizData.id)
        }
    }

    private fun populateView(quiz: Quiz) {
        val radioButton = radioGroup.getChildAt(quiz.correctans) as RadioButton
        radioButton.isChecked = true
        tet_question_editquestionfrag.setText(quiz.question)
        tet_option1.setText(quiz.option1)
        tet_option2.setText(quiz.option2)
        tet_option3.setText(quiz.option3)
        iv_upload.setImageBitmap(quiz.image?.let {
            com.example.quizadmin.utils.Helper.getIaeFromByteArray(
                it
            )
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                resultUri?.let {
                    compressedImageFile = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        it
                    )
                    Picasso.get().load(resultUri).into(iv_upload)

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}