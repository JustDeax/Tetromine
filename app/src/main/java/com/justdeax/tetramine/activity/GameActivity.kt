package com.justdeax.tetramine.activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityGameBinding
import com.justdeax.tetramine.util.applySystemInsets

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.main.applySystemInsets()

        val field = arrayOf(
            intArrayOf(3, 0, 7, 4, 6, 2, 5, 1, 2, 3),
            intArrayOf(7, 0, 6, 4, 2, 1, 5, 7, 3, 0),
            intArrayOf(1, 6, 2, 3, 0, 5, 4, 7, 3, 1),
            intArrayOf(2, 6, 0, 4, 7, 5, 2, 1, 6, 3),
            intArrayOf(4, 5, 0, 7, 1, 3, 6, 2, 0, 7),
            intArrayOf(5, 3, 1, 2, 6, 0, 4, 7, 2, 5),
            intArrayOf(2, 3, 6, 0, 5, 1, 7, 4, 2, 6),
            intArrayOf(0, 5, 3, 7, 1, 2, 0, 4, 6, 3),
            intArrayOf(5, 7, 1, 6, 3, 0, 2, 4, 7, 1),
            intArrayOf(5, 0, 6, 3, 4, 2, 7, 1, 0, 5),
            intArrayOf(0, 2, 4, 7, 1, 6, 3, 5, 2, 0),
            intArrayOf(7, 1, 4, 6, 2, 5, 3, 0, 7, 1),
            intArrayOf(6, 4, 3, 5, 0, 2, 1, 7, 6, 4),
            intArrayOf(2, 0, 3, 5, 7, 1, 6, 0, 3, 4),
            intArrayOf(1, 7, 5, 2, 4, 6, 0, 3, 1, 7),
            intArrayOf(4, 6, 2, 5, 3, 0, 7, 1, 6, 2),
            intArrayOf(3, 6, 0, 4, 7, 5, 2, 1, 6, 3),
            intArrayOf(0, 5, 7, 4, 1, 2, 0, 6, 5, 3),
            intArrayOf(2, 5, 7, 1, 3, 0, 6, 4, 5, 2),
            intArrayOf(1, 7, 0, 6, 3, 4, 1, 5, 2, 0),
        )

        val previewField = arrayOf(
            intArrayOf(0, 1, 0, 0),
            intArrayOf(1, 1, 1, 0),
            intArrayOf(0, 1, 0, 0),
        )

        binding.boardView.setColors(
            intArrayOf(
                ContextCompat.getColor(this, R.color.empty),
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.magenta),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.grey)
            )
        )

        binding.preview.setColors(
            intArrayOf(
                ContextCompat.getColor(this, R.color.invisible),
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.magenta),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.grey)
            )
        )

        binding.boardView.updateBoard(field)

        binding.preview.updateBoard(previewField)
    }
}