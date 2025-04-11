package com.justdeax.tetramine.util
import android.content.Context
import java.util.Locale

fun Context.setStatistics(linesCount: Int, scoreValue: Int): String {
    val lines = String.format(Locale.getDefault(), "%03d", linesCount)
    val score = String.format(Locale.getDefault(), "%06d", scoreValue)
    return "Lines:$lines   \nScore:$score"
}
