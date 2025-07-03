package com.example.taskpro.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskpro.presentation.components.BottomNavigationBar
import com.example.taskpro.presentation.components.CustomBottomBar
import com.example.taskpro.presentation.screens.create_project.CreateProjectScreen
import com.example.taskpro.presentation.screens.home.HomeScreen
import com.example.taskpro.presentation.screens.settings.SettingsScreen
import com.example.taskpro.presentation.screens.splash.SplashScreen
import com.example.taskpro.presentation.screens.task_board.TaskBoardScreen
import com.example.taskpro.ui.theme.TaskProTheme

@Composable
fun MyApp() {
    TaskProTheme {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        val showBottomBar = currentRoute in BottomNavItem.items.map { it.route }

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    CustomBottomBar(
                        navController = navController,
                        onFabClick = {
                            navController.navigate("addProject")
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("splash") {
                    SplashScreen {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
                composable("home") {
                    HomeScreen(onProjectClick = { projectId ->
                        navController.navigate("projectDetail/$projectId")
                    })
                }
                composable("addProject") {
                    CreateProjectScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("settings") {
                    SettingsScreen()
                }
                composable("projectDetail/{projectId}") { backStackEntry ->
                    val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
                    TaskBoardScreen (
                        modifier = Modifier,
                        projectId = projectId,
                        onBackClick = { navController.popBackStack() },
                        onFabClick = {}
                    )
                }
            }
        }
    }
}