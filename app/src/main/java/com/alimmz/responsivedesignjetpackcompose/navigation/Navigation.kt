package com.alimmz.responsivedesignjetpackcompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.alimmz.responsivedesignjetpackcompose.data.Notification
import com.alimmz.responsivedesignjetpackcompose.data.sampleNotifications
import com.alimmz.responsivedesignjetpackcompose.screens.HomeScreen
import com.alimmz.responsivedesignjetpackcompose.screens.NotificationsScreen
import com.alimmz.responsivedesignjetpackcompose.screens.TasksScreen
import com.alimmz.responsivedesignjetpackcompose.screens.CreateTaskScreen

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    data object Home : Screen(
        route = "home",
        icon = { Icon(Icons.Default.Home, contentDescription = null) },
        label = "Home"
    )
    data object Tasks : Screen(
        route = "tasks",
        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
        label = "Tasks"
    )
    data object CreateTask : Screen(
        route = "create-task",
        icon = { Icon(Icons.Default.Add, contentDescription = null) },
        label = "Create Task"
    )
    data object Notifications : Screen(
        route = "notifications",
        icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
        label = "Notifications"
    )
    data object NotificationDetail : Screen(
        route = "notification/{notificationId}",
        icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
        label = "Notification Detail"
    ) {
        fun createRoute(notificationId: String) = "notification/$notificationId"
    }
}

val screens = listOf(
    Screen.Home,
    Screen.Tasks,
    Screen.Notifications
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                icon = screen.icon,
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationRail(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationRailItem(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                icon = screen.icon,
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    isLargeScreen: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedNotification by remember { mutableStateOf<Notification?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                selectedNotification = selectedNotification,
                onNotificationSelected = { notification ->
                    if (isLargeScreen) {
                        if (notification.id == selectedNotification?.id) {
                            selectedNotification = null
                        } else {
                            selectedNotification = notification
                        }
                    } else {
                        navController.navigate(Screen.NotificationDetail.createRoute(notification.id))
                    }
                },
                isLargeScreen = isLargeScreen
            )
        }
        composable(Screen.Tasks.route) {
            TasksScreen(
                onAddTask = {
                    if (isLargeScreen) {
                        navController.navigate(Screen.CreateTask.route) {
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(Screen.CreateTask.route)
                    }
                },
                isLargeScreen = isLargeScreen
            )
        }
        composable(Screen.CreateTask.route) {
            CreateTaskScreen(
                onTaskCreated = { task ->
                    // TODO: Handle task creation
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                selectedNotification = selectedNotification,
                onNotificationSelected = { notification ->
                    if (isLargeScreen) {
                        if (notification.id == selectedNotification?.id) {
                            selectedNotification = null
                        } else {
                            selectedNotification = notification
                        }
                    } else {
                        navController.navigate(Screen.NotificationDetail.createRoute(notification.id))
                    }
                },
                isLargeScreen = isLargeScreen
            )
        }
        composable(
            route = Screen.NotificationDetail.route,
            arguments = listOf(
                navArgument("notificationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val notificationId = backStackEntry.arguments?.getString("notificationId")
            val notification = sampleNotifications.find { it.id == notificationId }

            if (isLargeScreen) {
                selectedNotification = notification
                navController.navigate(Screen.Notifications.route) {
                    popUpTo(Screen.Notifications.route) { inclusive = true }
                }
            } else {
                NotificationsScreen(
                    selectedNotification = notification,
                    onNotificationSelected = { newNotification ->
                        if (newNotification.id == notification?.id) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(Screen.NotificationDetail.createRoute(newNotification.id)) {
                                popUpTo(Screen.NotificationDetail.route)
                            }
                        }
                    },
                    isLargeScreen = isLargeScreen
                )
            }
        }
    }
} 