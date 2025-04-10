package com.justdeax.tetramine.game
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TetramineGameFactory(private val rows: Int, private val cols: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TetramineGameViewModel::class.java))
            return TetramineGameViewModel(rows, cols) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}