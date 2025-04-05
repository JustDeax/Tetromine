package com.justdeax.tetramine.game
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

abstract class BaseBoardView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    protected abstract var rows: Int
    protected abstract var cols: Int
    protected abstract var board: Array<IntArray>
    private val cellSpacing = 4f
    private val cornerRadius = 20f
    private var colors = intArrayOf()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val rect = RectF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val calculatedHeight = width * (rows.toFloat() / cols.toFloat())
        val finalWidth: Int
        val finalHeight: Int

        if (calculatedHeight > height) {
            finalHeight = height
            finalWidth = (height * (cols.toFloat() / rows.toFloat())).toInt()
        } else {
            finalWidth = width
            finalHeight = calculatedHeight.toInt()
        }
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cellSize = (width / cols.toFloat()) - cellSpacing
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val left = col * (cellSize + cellSpacing)
                val top = row * (cellSize + cellSpacing)
                val right = (col + 1) * (cellSize + cellSpacing) - cellSpacing
                val bottom = (row + 1) * (cellSize + cellSpacing) - cellSpacing

                paint.color = colors[(board[row][col])]
                rect.set(left, top, right, bottom)
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
            }
        }
    }

    fun setColors(newColors: IntArray) {
        colors = newColors
        invalidate()
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