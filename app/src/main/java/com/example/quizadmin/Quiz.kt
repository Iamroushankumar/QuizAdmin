package com.example.quizadmin

import androidx.annotation.IntegerRes
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class Quiz(
    @Id var id: Long = 0,
    var question: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
var correctans:Int = -1
) {
}