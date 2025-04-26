package com.alimmz.responsivedesignjetpackcompose.app

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alimmz.responsivedesignjetpackcompose.components.BottomNavigationBar
import com.alimmz.responsivedesignjetpackcompose.components.NavigationSideBar

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    activity: ComponentActivity,
    viewModel: MainViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Row(modifier = modifier) {
        val windowSizeClass = calculateWindowSizeClass(
            activity = activity,
        )
        val isMobile = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
        AnimatedVisibility(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            visible = !isMobile,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        ) {
            NavigationSideBar(
                selectedItemIndex = uiState.bottomNavSelectedIndex,
                onSelectItem = { index ->
                    viewModel.changeBottomNavSelectedIndex(index)
                }
            )
        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isMobile,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
                    BottomNavigationBar(
                        selectedItemIndex = uiState.bottomNavSelectedIndex,
                        onSelectItem = { index ->
                            viewModel.changeBottomNavSelectedIndex(index)
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
        }
    }
}