package com.justdeax.tetramine.activity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityGameBinding
import com.justdeax.tetramine.databinding.DialogPauseGameBinding
import com.justdeax.tetramine.game.TetromineGameFactory
import com.justdeax.tetramine.game.TetromineGameViewModel
import com.justdeax.tetramine.util.applySystemInsets
import com.justdeax.tetramine.util.notAvailable
import com.justdeax.tetramine.util.padArrayTo2x4
import com.justdeax.tetramine.util.setStatistics
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var dialogPauseBinding: DialogPauseGameBinding? = null
    private var dialogPauseGame: AlertDialog? = null
    private var colors = intArrayOf()
    private var boardColor = intArrayOf()
    private var previewColor = intArrayOf()
    private val rows = 20
    private val cols = 10
    private val game: TetromineGameViewModel by viewModels {
        TetromineGameFactory(rows, cols)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        colors = intArrayOf(
            getColor(R.color.cyan),
            getColor(R.color.yellow),
            getColor(R.color.magenta),
            getColor(R.color.orange),
            getColor(R.color.blue),
            getColor(R.color.green),
            getColor(R.color.red),
            getColor(R.color.ghost)
        )
        boardColor = intArrayOf(getColor(R.color.empty))
        previewColor = intArrayOf(getColor(R.color.invisible))

        binding.apply {
            setContentView(root)
            main.applySystemInsets()
            board.setColors(boardColor + colors)
            preview.setColors(previewColor + colors)
            board.setControls()
            pause.setOnClickListener { showPauseDialog() }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    game.board.collectLatest { newBoard ->
                        board.updateBoard(newBoard)
                        preview.updateBoard(padArrayTo2x4(game.previousPiece.shape))
                        statistics.text = setStatistics(game.lines, game.score)
                    }
                }
            }
            game.resumeGame()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { showPauseDialog() }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun View.setControls() {
        val xSensitivity = 0.55
        val ySensitivity = 0.40
        val halfCols = cols / 2
        val halfRows = rows / 2
        var lastTouchX = 0f
        var lastTouchY = 0f
        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = event.x - lastTouchX
                    val diffY = event.y - lastTouchY
                    val thresholdX = this.width / halfCols * xSensitivity
                    val thresholdY = this.height / halfRows * ySensitivity

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
                    this.invalidate()
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

    private fun showPauseDialog() {
        game.stopGame()
        if (dialogPauseBinding == null || dialogPauseGame == null) {
            dialogPauseBinding = DialogPauseGameBinding.inflate(LayoutInflater.from(this))
            dialogPauseGame = MaterialAlertDialogBuilder(this)
                .setView(dialogPauseBinding?.root)
                .setOnDismissListener { game.resumeGame() }
                .create()

            dialogPauseBinding?.apply {
                resume.setOnClickListener {
                    dialogPauseGame?.dismiss()
                }
                help.setOnClickListener {
                    notAvailable(this@GameActivity, getString(R.string.help))
                }
                exit.setOnClickListener {
                    dialogPauseGame?.dismiss()
                    finish()
                }
                restart.setOnClickListener {
                    dialogPauseGame?.dismiss()
                    game.startGame()
                }
            }
        }
        dialogPauseBinding?.apply {
            preview.setColors(boardColor + colors)
            preview.updateBoard(padArrayTo2x4(game.currentPiece.shape))
            statistics.text = setStatistics(game.lines, game.score)
            dialogPauseGame?.show()
        }
    }

    override fun onStop() {
        showPauseDialog()
        super.onStop()
    }
}