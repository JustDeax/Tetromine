package com.justdeax.tetramine.activity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityGameBinding
import com.justdeax.tetramine.game.TetromineGameFactory
import com.justdeax.tetramine.game.TetromineGameViewModel
import com.justdeax.tetramine.util.applySystemInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val game: TetromineGameViewModel by viewModels {
        TetromineGameFactory(rows, cols)
    }
    private val rows = 20
    private val cols = 10

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
            getColor(R.color.grey)
        )

        binding.apply {
            setContentView(root)
            main.applySystemInsets()
            boardView.setColors(intArrayOf(getColor(R.color.empty)) + colors)
            preview.setColors(intArrayOf(getColor(R.color.invisible)) + colors)
            setControls()
            //startStopButton.setOnClickListener { tetrisGame.startGame() }
            //pauseButton.setOnClickListener { tetrisGame.stopGame() }
            //resumeButton.setOnClickListener { tetrisGame.resumeGame() }

            lifecycleScope.launch {
                delay(100)
                //game.startGame()
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    game.board.debounce(50).collectLatest { board ->
                        boardView.updateBoard(board)
                        //statistics.text = setStatistics(game.lines, game.score)
                    }
                }
            }
        }
    }

    private fun setStatistics(lines: Int, score: Int): String {
        val lines =  getString(R.string.lines) + ": " + lines.toString()
        val score =  getString(R.string.score) + ": " + score.toString()
        return "$lines\n$score"
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setControls() {
        val boardView = binding.boardView
        val xSensitivity = 1.00
        val ySensitivity = 0.75
        val halfCols = cols / 2
        val halfRows = rows / 2
        var lastTouchX = 0f
        var lastTouchY = 0f
        boardView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = event.x - lastTouchX
                    val diffY = event.y - lastTouchY
                    val thresholdX = boardView.width / halfCols * xSensitivity
                    val thresholdY = boardView.height / halfRows * ySensitivity

                    if (abs(diffX) > thresholdX && abs(diffY) < thresholdY) {
                        //if (diffX > 0) game.moveRight()
                        //else game.moveLeft()
                        lastTouchX = event.x
                    }

                    if (diffY > thresholdY) {
                        //game.dropPiece()
                        lastTouchY = event.y
                    }

                    boardView.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    val diffX = abs(event.x - lastTouchX)
                    val diffY = abs(event.y - lastTouchY)
                    //if (diffX < 5 && diffY < 5) game.rotatePiece()

                    lastTouchX = -1f
                    lastTouchY = -1f
                }
            }
            true
        }
    }
}