package com.justdeax.tetramine.activity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityGameBinding
import com.justdeax.tetramine.databinding.DialogGameBinding
import com.justdeax.tetramine.game.TetromineGameFactory
import com.justdeax.tetramine.game.TetromineGameViewModel
import com.justdeax.tetramine.game.Tetromino
import com.justdeax.tetramine.util.applySystemInsets
import com.justdeax.tetramine.util.padArray2x4
import com.justdeax.tetramine.util.setStatistics
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var dialogGameBinding: DialogGameBinding? = null
    private var dialogGame: AlertDialog? = null
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
            getColor(R.color.cyan), //I
            getColor(R.color.yellow), //O
            getColor(R.color.magenta), //T
            getColor(R.color.orange), //L
            getColor(R.color.blue), //J
            getColor(R.color.green), //S
            getColor(R.color.red), //Z
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
                        preview.updateBoard(padArray2x4(game.previousPiece.shape))
                        statistics.text = setStatistics(game.lines, game.score)
                        if (game.isGameOver)
                            showGameOver()
                    }
                }
            }
            game.resumeGame()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { showPauseDialog() }
        })
    }

    private fun showPauseDialog() {
        game.stopGame()
        if (dialogGameBinding == null || dialogGame == null)
            initGameDialog()
        dialogGameBinding?.apply {
            preview.setColors(previewColor + colors)
            preview.updateBoard(
                if (game.currentPiece.shape == Tetromino.TETROMINO_SHAPES[1])
                    arrayOf(intArrayOf(0, 2, 2, 0), intArrayOf(0, 2, 2, 0))
                else
                    padArray2x4(game.currentPiece.shape)
            )
            statistics.text = setStatistics(game.lines, game.score)
            dialogGame?.show()
        }
    }

    private fun showGameOver() {
        if (dialogGameBinding == null || dialogGame == null)
            initGameDialog()
        dialogGameBinding?.apply {
            preview.visibility = View.GONE
            gameOver.visibility = View.VISIBLE
            resume.text = getString(R.string.view_game)
            statistics.text = setStatistics(game.lines, game.score)
            dialogGame?.setOnDismissListener { dialogGame = null }
            dialogGame?.show()
        }
    }

    private fun initGameDialog() {
        dialogGameBinding = DialogGameBinding.inflate(LayoutInflater.from(this))
        dialogGame = MaterialAlertDialogBuilder(this)
            .setView(dialogGameBinding?.root)
            .setOnDismissListener { game.resumeGame() }
            .create()

        dialogGameBinding?.apply {
            resume.setOnClickListener {
                dialogGame?.dismiss()
            }
            help.setOnClickListener {
                helpDialog()
            }
            exit.setOnClickListener {
                dialogGame?.dismiss()
                finish()
            }
            restart.setOnClickListener {
                dialogGame?.dismiss()
                game.startGame()
            }
        }
    }

    private fun helpDialog() {
        val imageView = ImageView(this)

        Glide.with(this)
            .asGif()
            .load(R.drawable.help_with_controllers)
            .into(imageView)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(imageView)
            .setPositiveButton("OK") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun View.setControls() {
        val xSensitivity = 0.6
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

    override fun onStop() {
        showPauseDialog()
        super.onStop()
    }
}