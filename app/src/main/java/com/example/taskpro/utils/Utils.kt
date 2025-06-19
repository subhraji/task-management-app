package com.example.taskpro.utils

import android.app.Activity

fun setScreenOrientation(activity: Activity, orientation: Int) {
    activity.requestedOrientation = orientation
}