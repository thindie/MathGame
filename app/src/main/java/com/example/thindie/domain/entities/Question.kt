package com.example.thindie.domain.entities

 class Question(
    val sum: Int,
    val visibleNumber: Int,
    val solution: Int,
    val listOfVariants: List<Int>
) {
    companion object
    const {
        val VARIANTS_OF_ANSWERS: Int = 6
    }
}


