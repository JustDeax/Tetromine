package com.justdeax.tetramine.game
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TetromineGameViewModel(rows: Int, cols: Int) : ViewModel() {
    private val tetromine = Tetromine(rows, cols)
    private var updateTime = 800L
    private var dropSpeed = updateTime
    private var gameJob: Job? = null

    val currentPiece get() = tetromine.currentPiece
    val previousPiece get() = tetromine.previousPiece
    val isGameOver get() = tetromine.isGameOver
    val lines get() = tetromine.lines
    val score get() = tetromine.score

    private val _board = MutableStateFlow(tetromine.getBoardWithPiece())
    val board = _board.asStateFlow()

    fun startGame() { resetGame(); resumeGame() }

    fun resumeGame() {
        if (!tetromine.isGameOver && gameJob == null)
            gameJob = viewModelScope.launch(Dispatchers.Default) {
                while (tetromine.isGameOver) {
                    delay(dropSpeed)
                    withContext(Dispatchers.Main.immediate) {
                        tetromine.dropPiece()
                        _board.value = tetromine.getBoardWithPiece()
                    }
                }
                withContext(Dispatchers.Main.immediate) {
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
        tetromine.resetGame()
        _board.value = tetromine.getBoardWithPiece()
    }

    fun moveLeft() {
        if (!tetromine.isGameOver) {
            tetromine.moveLeft()
            _board.value = tetromine.getBoardWithPiece()
        }
    }

    fun moveRight() {
        if (!tetromine.isGameOver) {
            tetromine.moveRight()
            _board.value = tetromine.getBoardWithPiece()
        }
    }

    fun rotatePiece() {
        if (!tetromine.isGameOver) {
            tetromine.rotatePiece()
            _board.value = tetromine.getBoardWithPiece()
        }
    }

    fun dropPiece() {
        if (!tetromine.isGameOver) {
            tetromine.dropPiece()
            tetromine.score += one
            _board.value = tetromine.getBoardWithPiece()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopGame()
    }
}