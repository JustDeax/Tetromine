package com.justdeax.tetramine.game

class Tetromino(val shape: Array<IntArray>, var row: Int = 0, var col: Int = 0) {
    fun rotate(): Tetromino {
        val newShape = Array(shape[0].size) { IntArray(shape.size) }
        for (i in shape.indices)
            for (j in shape[i].indices)
                newShape[j][shape.size - 1 - i] = shape[i][j]
        return Tetromino(newShape, row, col)
    }

    fun copy(): Tetromino {
        val newShape = shape.map { it.clone() }.toTypedArray()
        return Tetromino(newShape, row, col)
    }

    companion object {
        val TETROMINO_SHAPES = listOf(
            arrayOf( // I
                intArrayOf(0, 0, 0, 0),
                intArrayOf(1, 1, 1, 1),
                intArrayOf(0, 0, 0, 0),
            ),
            arrayOf( // O
                intArrayOf(2, 2),
                intArrayOf(2, 2),
            ),
            arrayOf( // T
                intArrayOf(0, 3, 0),
                intArrayOf(3, 3, 3),
                intArrayOf(0, 0, 0),
            ),
            arrayOf( // L
                intArrayOf(4, 0, 0),
                intArrayOf(4, 4, 4),
                intArrayOf(0, 0, 0),
            ),
            arrayOf( // J
                intArrayOf(0, 0, 5),
                intArrayOf(5, 5, 5),
                intArrayOf(0, 0, 0),
            ),
            arrayOf( // S
                intArrayOf(0, 6, 6),
                intArrayOf(6, 6, 0),
                intArrayOf(0, 0, 0),
            ),
            arrayOf( // Z
                intArrayOf(7, 7, 0),
                intArrayOf(0, 7, 7),
                intArrayOf(0, 0, 0),
            ),
        )

        fun randomPiece() = Tetromino(TETROMINO_SHAPES.random())
        fun emptyPiece() = Tetromino(arrayOf(intArrayOf()))
    }
}