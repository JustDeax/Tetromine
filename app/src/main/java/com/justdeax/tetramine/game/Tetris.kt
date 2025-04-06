package com.justdeax.tetramine.game

class Tetris(private val rows: Int, private val cols: Int) {
    private var board = Array(rows) { IntArray(cols) }
    var currentPiece = Tetromino.emptyPiece(); private set
    var previousPiece = Tetromino.getRandomPiece() ; private set
    var isGameOver = false ; private set
    var score = 0

    init { spawnPiece() }

    fun resetGame() {
        board = Array(rows) { IntArray(cols) }
        currentPiece = Tetromino.emptyPiece()
        previousPiece = Tetromino.getRandomPiece()
        isGameOver = false
        score = 0
        spawnPiece()
    }

    fun getBoardWithPiece(): Array<IntArray> {
        val board = board.map { it.clone() }.toTypedArray()
        applyPieceToBoard(board, ghostPiece(), true)
        applyPieceToBoard(board, currentPiece)
        return board
    }

    fun dropPiece() {
        if (!movePiece(1, 0)) {
            placePiece()
            clearLines()
            spawnPiece()
        }
    }

    fun moveLeft() = movePiece(0, -1)
    fun moveRight() = movePiece(0, 1)

    fun rotatePiece() {
        val rotated = currentPiece.rotate()
        if (isValidMove(rotated, currentPiece.row, currentPiece.column))
            currentPiece = rotated
    }

    private fun movePiece(dRow: Int, dCol: Int): Boolean {
        if (isValidMove(currentPiece, currentPiece.row + dRow, currentPiece.column + dCol)) {
            currentPiece.row += dRow
            currentPiece.column += dCol
            return true
        }
        return false
    }

    private fun isValidMove(piece: Tetromino, newRow: Int, newCol: Int): Boolean {
        return forEachCell(piece) { i, j ->
            val row = newRow + i
            val col = newCol + j
            row in 0 until rows && col in 0 until cols && board[row][col] == 0
        }
    }

    private fun placePiece() {
        applyPieceToBoard(board, currentPiece)
    }

    private fun clearLines() {
        board = board.filter { row -> row.any { it == 0 } }.toTypedArray()
        val clearedLines = rows - board.size
        while (board.size < rows)
            board = arrayOf(IntArray(cols)) + board
        when (clearedLines) {
            1 -> score += 100
            2 -> score += 200
            3 -> score += 300
            4 -> score += 400
        }
    }

    private fun spawnPiece() {
        currentPiece = previousPiece
        previousPiece = Tetromino.getRandomPiece()
        currentPiece.row = 0
        currentPiece.column = cols / 2 - (currentPiece.shape[0].size / 2)
        if (!isValidMove(currentPiece, currentPiece.row, currentPiece.column))
            isGameOver = true
    }

    private fun ghostPiece(): Tetromino {
        val ghost = currentPiece.copy()
        while (isValidMove(ghost, ghost.row + 1, ghost.column))
            ghost.row += 1
        return ghost
    }

    private fun applyPieceToBoard(board: Array<IntArray>, piece: Tetromino, isGhost: Boolean = false) {
        forEachCell(piece) { i, j ->
            val row = piece.row + i
            val col = piece.column + j
            if (row in board.indices && col in board[0].indices)
                board[row][col] = if (isGhost) 8 else piece.shape[i][j]
            true
        }
    }

    private inline fun forEachCell(piece: Tetromino, action: (Int, Int) -> Boolean): Boolean {
        for (i in piece.shape.indices)
            for (j in piece.shape[i].indices)
                if (piece.shape[i][j] != 0 && !action(i, j)) return false
        return true
    }
}