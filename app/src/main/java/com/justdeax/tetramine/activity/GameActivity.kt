package com.justdeax.tetramine.activity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityGameBinding
import com.justdeax.tetramine.game.TetromineGameFactory
import com.justdeax.tetramine.game.TetromineGameViewModel
import com.justdeax.tetramine.util.applySystemInsets
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val rows = 20
    private val cols = 10
    private val game: TetromineGameViewModel by viewModels {
        TetromineGameFactory(rows, cols)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        val colors = intArrayOf(
            getColor(R.color.cyan),
            getColor(R.color.yellow),
            getColor(R.color.magenta),
            getColor(R.color.orange),
            getColor(R.color.blue),
            getColor(R.color.green),
            getColor(R.color.red),
            getColor(R.color.ghost)
        )
        val boardColor = intArrayOf(getColor(R.color.empty))
        val previewColor = intArrayOf(getColor(R.color.invisible))

        binding.apply {
            setContentView(root)
            main.applySystemInsets()

            board.setColors(boardColor + colors)
            preview.setColors(previewColor + colors)
            //startStopButton.setOnClickListener { tetrisGame.startGame() }
            //pauseButton.setOnClickListener { tetrisGame.stopGame() }
            //resumeButton.setOnClickListener { tetrisGame.resumeGame() }

            setControls()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    game.board.collectLatest { newBoard ->
                        board.updateBoard(newBoard)
                        statistics.text = setStatistics(game.lines, game.score)
                        preview.updateBoard(padArrayTo3x4(game.previousPiece.shape))
                    }
                }
            }
            game.startGame()
        }
    }

    private fun setStatistics(lines: Int, score: Int): String {
        val lines =  getString(R.string.lines) + ": " + lines.toString()
        val score =  getString(R.string.score) + ": " + score.toString()
        return "$lines\n$score"
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setControls() {
        val board = binding.board
        val xSensitivity = 0.55
        val ySensitivity = 0.40
        val halfCols = cols / 2
        val halfRows = rows / 2
        var lastTouchX = 0f
        var lastTouchY = 0f
        board.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = event.x - lastTouchX
                    val diffY = event.y - lastTouchY
                    val thresholdX = board.width / halfCols * xSensitivity
                    val thresholdY = board.height / halfRows * ySensitivity

                    if (abs(diffX) > thresholdX && abs(diffY) < thresholdY) {
                        if (diffX > 0)
                            game.moveRight()
                        else
                            game.moveLeft()
                        lastTouchX = event.x
                    } else if (diffY > thresholdY) {
                        game.dropPiece()
                        lastTouchY = event.y
                    }
                    board.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    val diffX = abs(event.x - lastTouchX)
                    val diffY = abs(event.y - lastTouchY)
                    if (diffX < 3 && diffY < 3)
                        game.rotatePiece()
                    lastTouchX = -1f
                    lastTouchY = -1f
                }
            }
            true
        }
    }

    fun padArrayTo3x4(array: Array<IntArray>): Array<IntArray> {
        return Array(3) { rowIndex ->
            val row = array.getOrNull(rowIndex) ?: intArrayOf()
            val paddedRow = IntArray(4)
            for (i in row.indices)
                if (i < 4) paddedRow[4 - row.size + i] = row[i]
            paddedRow
        }
    }
}