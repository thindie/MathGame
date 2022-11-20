package com.example.thindie.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.thindie.R
import com.example.thindie.domain.entities.GameResults

@BindingAdapter("tv_solvedQuestions_adapter")
fun adaptSolved(tv: TextView, solved: Int) {
    tv.text = String.format("Ваш счет:  %s", solved.toString())
}

@BindingAdapter("tv_total_adapter")
fun adaptTotal(tv: TextView, total: Int) {
    tv.text = String.format("Всего вопросов:  %s", total.toString())
}

@BindingAdapter("im_adapter")
fun adaptTotal(im: ImageView, isWinner: Boolean) {
    if (isWinner) {
        im.setImageResource(R.drawable.happy)
    } else im.setImageResource(R.drawable.sad)
}

@BindingAdapter("tv_percentage")
fun adaptPercentage(tv: TextView, gameResults: GameResults) {
    tv.text = String.format("Процент правильных ответов:  %s", gameResults.winRate.toString())
}
