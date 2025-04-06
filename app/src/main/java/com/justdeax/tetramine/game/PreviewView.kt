package com.justdeax.tetramine.game
import android.content.Context
import android.util.AttributeSet

class PreviewView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 3
    override var cols = 4
    override var board = Array(rows) { IntArray(cols) { 0 } }
}