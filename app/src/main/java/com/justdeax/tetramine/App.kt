package com.justdeax.tetramine
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val (theme, dynamic) = 'S' to false

        AppCompatDelegate.setDefaultNightMode(
            when (theme) {
                'L' -> AppCompatDelegate.MODE_NIGHT_NO
                'D' -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
        if (dynamic) {
            DynamicColors.applyToActivitiesIfAvailable(this)
            setTheme(R.style.Theme_Dynamic)
        } else {
            setTheme(R.style.Theme_Static)
        }
    }
}
