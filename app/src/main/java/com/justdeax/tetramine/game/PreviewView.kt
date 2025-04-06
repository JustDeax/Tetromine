package com.justdeax.tetramine.game
import android.content.Context
import android.util.AttributeSet

class PreviewView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 2
    override var cols = 4
    override var board = Array(rows) { IntArray(cols) { 0 } }
    override fun updateBoard(newBoard: Array<IntArray>) {
        if (!areEqual(board, newBoard)) super.updateBoard(newBoard)
    }

    fun areEqual(a: Array<IntArray>, b: Array<IntArray>) =
        a.size == b.size && a.indices.all { a[it].contentEquals(b[it]) }
}