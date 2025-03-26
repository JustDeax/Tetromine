package com.justdeax.tetramine.activity
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityMainBinding
import com.justdeax.tetramine.util.applySystemInsets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.main.applySystemInsets()
    }

    override fun onStart() {
        super.onStart()
        val screenHeight = binding.main.height

        binding.logoLayout.layoutParams.height = screenHeight / 3
        binding.logoLayout.requestLayout()

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainMenu -> animateHeightChange(binding.logoLayout, screenHeight / 3)
                R.id.levels -> animateHeightChange(binding.logoLayout, screenHeight / 6)
            }
        }
    }

    fun animateHeightChange(
        view: View,
        newHeight: Int
    ) {
        val startHeight = view.height
        val animator = ValueAnimator.ofInt(startHeight, newHeight)

        animator.addUpdateListener { valueAnimator ->
            val updatedHeight = valueAnimator.animatedValue as Int
            view.layoutParams.height = updatedHeight
            view.requestLayout()
        }

        animator.duration = 500
        animator.start()
    }
}