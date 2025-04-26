package com.alimmz.responsivedesignjetpackcompose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alimmz.responsivedesignjetpackcompose.R

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int = 0,
    onSelectItem: (Int) -> Unit = {},
) {

    NavigationBar(
        contentColor = Color.Blue,
        containerColor = Color(0x322196F3)
    ) {
        listOf(
            Icons.TwoTone.Home to R.string.home,
            Icons.TwoTone.ShoppingCart to R.string.shop,
            Icons.TwoTone.Person to R.string.profile,
        ).forEachIndexed { index, item ->
            val isSelected by remember(selectedItemIndex) {
                derivedStateOf {
                    selectedItemIndex == index
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onSelectItem(index)
                        }
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = item.first,
                        tint = if (isSelected) Color.Black else Color.Gray,
                        contentDescription = "Home"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(item.second),
                        color = if (isSelected) Color.Black else Color.Gray,
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xfffff,
)
@Composable
private fun NavigationBarPreview() {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                BottomNavigationBar(
                    selectedItemIndex = 0,
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}