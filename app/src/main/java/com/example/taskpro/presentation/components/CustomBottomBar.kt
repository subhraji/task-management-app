    package com.example.taskpro.presentation.components

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.offset
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material3.BottomAppBar
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.FabPosition
    import androidx.compose.material3.FloatingActionButton
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.LocalContentColor
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import androidx.navigation.compose.currentBackStackEntryAsState
    import com.example.taskpro.presentation.navigation.BottomNavItem

    @Composable
    fun CustomBottomBar(
        navController: NavController,
        onFabClick: () -> Unit
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val isHomeSelected = currentRoute == BottomNavItem.Home.route
        val isSettingsSelected = currentRoute == BottomNavItem.settings.route

        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Home Button
                    IconButton(onClick = {
                        if (currentRoute != BottomNavItem.Home.route) {
                            navController.navigate(BottomNavItem.Home.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (isHomeSelected) BottomNavItem.Home.selectedIcon else BottomNavItem.Home.unselectedIcon,
                            contentDescription = "Home",
                            tint = if (currentRoute == BottomNavItem.Home.route)
                                MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }

                    Spacer(modifier = Modifier.width(48.dp)) // Space for FAB

                    // Settings Button
                    IconButton(onClick = {
                        if (currentRoute != BottomNavItem.settings.route) {
                            navController.navigate(BottomNavItem.settings.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (isSettingsSelected) BottomNavItem.settings.selectedIcon else BottomNavItem.settings.unselectedIcon,
                            contentDescription = "Settings",
                            tint = if (currentRoute == BottomNavItem.settings.route)
                                MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }
                }
            }

            // Floating Action Button (centered)
            FloatingActionButton(
                onClick = onFabClick,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-24).dp), // Pull it up to overlap bar
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }

