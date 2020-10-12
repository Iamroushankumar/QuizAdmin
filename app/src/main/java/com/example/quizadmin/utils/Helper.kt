package com.example.quizadmin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class Helper {

    companion object {
        fun getIaeFromByteArray(arr:ByteArray): Bitmap? {
         return   BitmapFactory.decodeByteArray(arr, 0, arr?.size?:0)
        }

         fun convertImagetoBytes(image: Bitmap?):ByteArray{
            val stream = ByteArrayOutputStream()
            image?.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            return image
        }
    }
}