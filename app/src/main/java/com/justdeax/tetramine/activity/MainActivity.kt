package com.justdeax.tetramine.activity
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.color.DynamicColors
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.ActivityMainBinding
import com.justdeax.tetramine.util.applySystemInsets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var screenHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        DynamicColors.applyToActivitiesIfAvailable(application)
        binding.main.applySystemInsets()
        binding.main.post {
            screenHeight = binding.main.height
            animateHeightChange(binding.logoLayout, screenHeight / 3)
        }
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainMenu -> {
                    binding.title.visibility = View.GONE
                }
                R.id.chooseMode -> {
                    binding.title.text = getString(R.string.choose_mode)
                    binding.title.visibility = View.VISIBLE
                }
                R.id.statistics -> {
                    binding.title.text = getString(R.string.statistics)
                    binding.title.visibility = View.VISIBLE
                }
                R.id.settings -> {
                    binding.title.text = getString(R.string.settings)
                    binding.title.visibility = View.VISIBLE
                }
                R.id.aboutGame -> {
                    binding.title.visibility = View.GONE
                }
            }

            if (screenHeight != 0) {
                if (destination.id == R.id.mainMenu)
                     animateHeightChange(binding.logoLayout, screenHeight / 3)
                else animateHeightToWrapContent(binding.logoLayout)
            }
        }
    }

    private fun animateHeightChange(view: View, newHeight: Int) {
        val startHeight = view.height
        val animator = ValueAnimator.ofInt(startHeight, newHeight)

        animator.addUpdateListener { valueAnimator ->
            val updatedHeight = valueAnimator.animatedValue as Int
            view.layoutParams.height = updatedHeight
            view.requestLayout()
        }

        animator.duration = 250
        animator.start()
    }

    private fun animateHeightToWrapContent(view: View) {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        animateHeightChange(view, view.measuredHeight)
    }
}