package com.justdeax.tetramine.util
import android.content.Context
import com.justdeax.tetramine.R
import java.util.Locale

fun Context.setStatistics(lines: Int, score: Int): String {
    val lines = getString(R.string.lines) + ":" + String.format(Locale.getDefault(), "%03d", lines)
    val score = getString(R.string.score) + ":" + String.format(Locale.getDefault(), "%06d", score)
    return "$lines\n$score"
}