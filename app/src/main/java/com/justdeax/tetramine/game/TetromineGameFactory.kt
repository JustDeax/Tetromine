package com.justdeax.tetramine.game
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TetromineGameFactory(private val rows: Int, private val cols: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TetromineGameViewModel::class.java))
            return TetromineGameViewModel(rows, cols) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}