package com.justdeax.tetramine

import android.content.Context

object PreferenceManager {
    private const val PREFERENCE_NAME = "preference"
    private const val FIRST_LAUNCH = "first_launch"

    fun Context.changeFirstLaunch(boolean: Boolean) {
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().apply {
            putBoolean(FIRST_LAUNCH, boolean)
            apply()
        }
    }

    fun Context.isFirstLaunch(): Boolean {
        return getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            .getBoolean(FIRST_LAUNCH, true)
    }
}