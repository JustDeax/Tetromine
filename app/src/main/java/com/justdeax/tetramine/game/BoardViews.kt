package com.justdeax.tetramine.game
import android.content.Context
import android.util.AttributeSet

class TetrisBoardView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 20
    override var cols = 10
}

class PreviewBoardView(context: Context, attrs: AttributeSet? = null) : BaseBoardView(context, attrs) {
    override var rows = 3
    override var cols = 4
}
