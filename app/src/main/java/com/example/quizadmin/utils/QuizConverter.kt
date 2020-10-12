package com.example.quizadmin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.objectbox.converter.PropertyConverter
import java.io.ByteArrayOutputStream

class QuizConverter : PropertyConverter<Bitmap, ByteArray> {
    override fun convertToEntityProperty(databaseValue: ByteArray?): Bitmap {

if(databaseValue!=null){
    BitmapFactory.decodeByteArray(databaseValue, 0, databaseValue?.size?:0)
}else{
    Log.d("DATA<><>","Not present")
}
       return  BitmapFactory.decodeByteArray(databaseValue, 0, databaseValue?.size?:0)


    }

    override fun convertToDatabaseValue(entityProperty: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        entityProperty?.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        return image
    }

}