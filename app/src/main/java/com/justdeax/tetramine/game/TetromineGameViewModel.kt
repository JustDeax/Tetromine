package com.justdeax.tetramine.game
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justdeax.tetramine.util.one
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TetromineGameViewModel(rows: Int, cols: Int): ViewModel() {
    private val tetromine = Tetromine(rows, cols)
    private var startedSpeed = 800L
    private var dropSpeed = startedSpeed
    private var gameJob: Job? = null

    val currentPiece get() = tetromine.currentPiece
    val previousPiece get() = tetromine.previousPiece
    val isGameOver get() = tetromine.isGameOver
    val lines get() = tetromine.lines
    val score get() = tetromine.score

    private val _board = MutableStateFlow(tetromine.getBoardWithPiece())
    val board = _board.asStateFlow()

    fun startGame() {
        resetGame()
        resumeGame()
    }

    fun resumeGame() {
        if (!tetromine.isGameOver && gameJob == null)
            gameJob = viewModelScope.launch(Dispatchers.Default) {
                while (!tetromine.isGameOver) {
                    delay(dropSpeed)
                    withContext(Dispatchers.Main) {
                        tetromine.dropPiece()
                        _board.value = tetromine.getBoardWithPiece()
                    }
                }
                withContext(Dispatchers.Main) {
                    stopGame()
                }
            }
    }

    fun stopGame() {
        gameJob?.cancel()
        gameJob = null
        _board.value = tetromine.getBoardWithPiece()
    }

    fun resetGame() {
        gameJob?.cancel()
        gameJob = null
        tetromine.resetGame()
        dropSpeed = startedSpeed
        _board.value = tetromine.getBoardWithPiece()
    }

    fun moveLeft() {
        performAction {
            tetromine.moveLeft()
        }
    }

    fun moveRight() {
        performAction {
            tetromine.moveRight()
        }
    }

    //fun rotateLeft() {}

    fun rotateRight() {
        performAction {
            tetromine.rotateRight()
        }
    }

    fun dropPiece() {
        performAction {
            tetromine.dropPiece()
            tetromine.score += one
        }
    }

    private fun performAction(action: () -> Unit) {
        if (!tetromine.isGameOver) {
            action()
            _board.value = tetromine.getBoardWithPiece()
        }
    }

    override fun onCleared() {
        stopGame()
        super.onCleared()
    }
}