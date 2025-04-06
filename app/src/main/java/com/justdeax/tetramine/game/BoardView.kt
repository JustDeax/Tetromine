package com.justdeax.tetramine.game
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.justdeax.tetramine.R

class BoardView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var rows = 20
    private var cols = 10
    private val cellSpacing = 4f
    private val cornerRadius = 20f
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val colors: IntArray = intArrayOf(
        context.getColor(R.color.empty),
        context.getColor(R.color.cyan),
        context.getColor(R.color.yellow),
        context.getColor(R.color.magenta),
        context.getColor(R.color.orange),
        context.getColor(R.color.blue),
        context.getColor(R.color.green),
        context.getColor(R.color.red),
        context.getColor(R.color.grey)
    )
    private val rect = RectF()
    var board: Array<IntArray> = Array(rows) { IntArray(cols) }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val calculatedHeight = width * (rows / cols)
        val finalWidth: Int
        val finalHeight: Int

        if (calculatedHeight > height) {
            finalHeight = height
            finalWidth = height / (rows / cols)
        } else {
            finalWidth = width
            finalHeight = calculatedHeight
        }
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cellSize = (width / cols.toFloat()) - cellSpacing
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                paint.color = colors[(board[row][col])]
                rect.set(
                    col * (cellSize + cellSpacing), row * (cellSize + cellSpacing),
                    (col + 1) * (cellSize + cellSpacing) - cellSpacing, (row + 1) * (cellSize + cellSpacing) - cellSpacing
                )
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
            }
        }
    }

    fun updateBoard(newBoard: Array<IntArray>) {
        board = newBoard
        invalidate()
    }

//    fun setBoardSize(rows: Int, cols: Int) {
//        this.rows = rows
//        this.cols = cols
//        board = Array(rows) { IntArray(cols) }
//        requestLayout()
//        invalidate()
//    }
}