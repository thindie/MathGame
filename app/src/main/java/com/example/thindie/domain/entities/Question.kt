package com.example.thindie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Question (
    val sum: Int,
    val visibleNumber: Int,
    val solution: Int,
    val listOfVariants: List<Int>
) : Parcelable {
    companion object
    const {
        val VARIANTS_OF_ANSWERS: Int = 6
    }
}


