package com.justdeax.tetramine.game
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.justdeax.tetramine.util.dpToPx

abstract class BaseBoardView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    protected abstract var rows: Int
    protected abstract var cols: Int
    protected abstract var board: Array<IntArray>

    private var colors = intArrayOf()
    private val cellSpacing = context.dpToPx(2f)
    private val cornerRadius = context.dpToPx(8f)
    private val rect = RectF()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val ratio = rows.toFloat() / cols
        val targetHeight = width * ratio
        val finalWidth: Int
        val finalHeight: Int

        if (targetHeight > height) {
            finalHeight = height
            finalWidth = (height / ratio).toInt()
        } else {
            finalWidth = width
            finalHeight = targetHeight.toInt()
        }
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cellSize = (width / cols.toFloat()) - cellSpacing
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val value = board[row][col]
                if (value in colors.indices) {
                    paint.color = colors[value]
                    val left = col * (cellSize + cellSpacing)
                    val top = row * (cellSize + cellSpacing)
                    val right = left + cellSize
                    val bottom = top + cellSize
                    rect.set(left, top, right, bottom)
                    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
                }
            }
        }
    }

    fun setColors(newColors: IntArray) {
        colors = newColors
        invalidate()
    }

    open fun updateBoard(newBoard: Array<IntArray>) {
        board = newBoard
        invalidate()
    }
}
