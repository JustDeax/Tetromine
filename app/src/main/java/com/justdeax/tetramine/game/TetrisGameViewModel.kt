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

class TetrisGameViewModel(rows: Int, cols: Int): ViewModel() {
    private val tetris = Tetris(rows, cols)
    private var updateTime = 900L
    private var dropSpeed = updateTime
    private var gameJob: Job? = null

    val currentPiece get() = tetris.currentPiece
    val previousPiece get() = tetris.previousPiece
    val isGameOver get() = tetris.isGameOver
    val score get() = tetris.score

    private val _board = MutableStateFlow(tetris.getBoardWithPiece())
    val board = _board.asStateFlow()

    fun startGame() { resetGame(); resumeGame() }

    fun resumeGame() {
        if (!tetris.isGameOver && gameJob == null)
            gameJob = viewModelScope.launch(Dispatchers.Default) { while (true) {
                delay(dropSpeed)
                withContext(Dispatchers.Main) {
                    if (!tetris.isGameOver) {
                        tetris.dropPiece()
                        _board.value = tetris.getBoardWithPiece()
                    } else {
                        stopGame()
                    }
                }
            } }
    }

    fun stopGame() {
        gameJob?.cancel()
        gameJob = null
        _board.value = tetris.getBoardWithPiece()
    }

    private fun resetGame() {
        tetris.resetGame()
        _board.value = tetris.getBoardWithPiece()
    }

    fun moveLeft() {
        if (!tetris.isGameOver) {
            tetris.moveLeft()
            _board.value = tetris.getBoardWithPiece()
        }
    }

    fun moveRight() {
        if (!tetris.isGameOver) {
            tetris.moveRight()
            _board.value = tetris.getBoardWithPiece()
        }
    }

    fun rotatePiece() {
        if (!tetris.isGameOver) {
            tetris.rotatePiece()
            _board.value = tetris.getBoardWithPiece()
        }
    }

    fun dropPiece() {
        if (!tetris.isGameOver) {
            tetris.dropPiece()
            tetris.score += 1
            _board.value = tetris.getBoardWithPiece()
        }
    }
}