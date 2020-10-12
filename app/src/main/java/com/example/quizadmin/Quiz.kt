package com.example.quizadmin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import com.example.quizadmin.utils.QuizConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import kotlinx.android.parcel.Parcelize
import java.io.ByteArrayOutputStream

@Parcelize
@Entity
data class Quiz(
    @Id var id: Long = 0,
    var question: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
    var correctans: Int = -1,
   var image:ByteArray?=null

) : Parcelable

