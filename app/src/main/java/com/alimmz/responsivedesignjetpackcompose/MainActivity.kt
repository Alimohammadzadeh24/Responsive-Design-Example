package com.alimmz.responsivedesignjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.alimmz.responsivedesignjetpackcompose.navigation.AppNavHost
import com.alimmz.responsivedesignjetpackcompose.navigation.BottomNavigationBar
import com.alimmz.responsivedesignjetpackcompose.navigation.NavigationRail
import com.alimmz.responsivedesignjetpackcompose.ui.theme.ResponsiveDesignExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResponsiveDesignExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isLargeScreen = screenWidth >= 600.dp

    if (isLargeScreen) {
        // Tablet layout with navigation rail
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail(navController = navController)
            Box(modifier = Modifier.weight(1f)) {
                AppNavHost(
                    navController = navController,
                    isLargeScreen = isLargeScreen
                )
            }
        }
    } else {
        // Phone layout with bottom navigation
        androidx.compose.material3.Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                isLargeScreen = isLargeScreen,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
