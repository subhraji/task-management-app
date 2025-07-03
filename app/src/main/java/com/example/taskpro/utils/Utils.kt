package com.example.taskpro.utils

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

fun setScreenOrientation(activity: Activity, orientation: Int) {
    activity.requestedOrientation = orientation
}

@Composable
fun isDark(): Boolean {
    return isSystemInDarkTheme()
}