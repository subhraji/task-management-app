package com.example.taskpro.presentation.screens.splash

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit){
    val isPreAndroid12 = remember {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S
    }

    LaunchedEffect(true) {
        if (isPreAndroid12) {
            delay(2000L)
        }
        onTimeout()
    }

    if (isPreAndroid12) {
        // Compose splash only visible for pre-Android 12
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("TaskPro", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}