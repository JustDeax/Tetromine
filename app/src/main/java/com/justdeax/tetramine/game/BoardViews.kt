package com.justdeax.tetramine.game
import android.content.Context
import android.util.AttributeSet

class TetrisBoardView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 20
    override var cols = 10
    override var board: Array<IntArray> = Array(rows) { IntArray(cols) { 0 } }
}

class PreviewBoardView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 3
    override var cols = 4
    override var board = Array(rows) { IntArray(cols) { 0 } }
}