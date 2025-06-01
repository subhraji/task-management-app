package com.example.taskpro.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskpro.presentation.navigation.MyApp
import com.example.taskpro.presentation.screens.home.HomeScreen
import com.example.taskpro.presentation.screens.splash.SplashScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Android 12 SplashScreen API
        installSplashScreen()

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContent {
            MyApp()
        }
    }
}