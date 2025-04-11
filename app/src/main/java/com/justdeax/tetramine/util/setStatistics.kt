package com.justdeax.tetramine.util
import android.content.Context
import com.justdeax.tetramine.R
import java.util.Locale

fun Context.setStatistics(linesCount: Int, scoreValue: Int): String {
    val lines = String.format(Locale.getDefault(), "%03d", linesCount)
    val score = String.format(Locale.getDefault(), "%06d", scoreValue)
    return getString(R.string.lines_0) + ":$lines   \n" + getString(R.string.score_0) + ":$score"
}
