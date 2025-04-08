package com.justdeax.tetramine.util
import android.content.Context
import com.justdeax.tetramine.R
import java.util.Locale

fun Context.setStatistics(linesCount: Int, scoreValue: Int, naming: Boolean = true): String {
    val lines = String.format(Locale.getDefault(), "%03d", linesCount)
    val score = String.format(Locale.getDefault(), "%06d", scoreValue)

    return if (naming)
        getString(R.string.o_lines_o) + ":$lines" + getString(R.string.o_score_o) + ":$score"
    else
        "$lines\n$score"
}
