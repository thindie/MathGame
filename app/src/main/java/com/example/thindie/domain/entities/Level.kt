package com.example.thindie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
enum class Level : Parcelable {
    TEST, EASY, NORMAl, HARD
}