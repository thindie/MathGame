package com.example.thindie.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.example.thindie.R
import com.example.thindie.domain.entities.GameResults

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}


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

@BindingAdapter("tv_stringInfo_colorState")
fun changeColor(textView: TextView, boolean: Boolean) {
    textView.setTextColor(getColorState(boolean))
}

@BindingAdapter("pb_incrementProgress")
fun incrementProgress(progressBar: ContentLoadingProgressBar, int: Int) {
    progressBar.incrementProgressBy(int)
}

@BindingAdapter("pb_incrementSecondaryProgress")
fun incrementSecondaryProgress(progressBar: ContentLoadingProgressBar, int: Int) {
    progressBar.incrementSecondaryProgressBy(int)
}

@BindingAdapter("pb_setBackgroundColor_changing")
fun pbBackGroundTintList(progressBar: ContentLoadingProgressBar, boolean: Boolean) {
    progressBar.progressBackgroundTintList.apply { ColorStateList.valueOf(getColorState(boolean)) }
}

@BindingAdapter("pb_setColorChanging")
fun pbTintList(progressBar: ContentLoadingProgressBar, boolean: Boolean) {
    progressBar.progressTintList.apply { ColorStateList.valueOf(getColorState(boolean)) }
}

@BindingAdapter("tv_setSum")
fun setSum(textView: TextView, int: Int) {
    textView.text = int.toString()
}

@BindingAdapter("tv_optionClickListener")
fun tvOnOptionClickListener(textView: TextView, optionClickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        optionClickListener.onOptionClick(textView.text.toString().toInt())
    }
}

fun getColorState(boolean: Boolean): Int {
    return if (boolean) {
        Color.GREEN
    } else Color.RED
}