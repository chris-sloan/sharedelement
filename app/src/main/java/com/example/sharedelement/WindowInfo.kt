package com.example.sharedelement

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    return remember {
        WindowInfo(
            screenWidthInfo = when {
                configuration.screenWidthDp < 600 -> WindowSize.Compact
                configuration.screenWidthDp < 840 -> WindowSize.Medium
                else -> WindowSize.Expanded
            },
            screenHeightInfo = when {
                configuration.screenHeightDp < 480 -> WindowSize.Compact
                configuration.screenHeightDp < 900 -> WindowSize.Medium
                else -> WindowSize.Expanded
            },
            screenWidth = configuration.screenWidthDp.dp,
            screenHeight = configuration.screenHeightDp.dp,
            isLandscape = isLandscape,
        )
    }
}

data class WindowInfo(
    val screenWidthInfo: WindowSize,
    val screenHeightInfo: WindowSize,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val isLandscape: Boolean,
) {
    val isTablet = screenWidthInfo !is WindowSize.Compact && screenHeightInfo !is WindowSize.Compact
    val isSmartphone = !isTablet
}

sealed class WindowSize {
    object Compact : WindowSize() // Smartphone portrait
    object Medium : WindowSize() // smaller tablets
    object Expanded : WindowSize() // Big tablets
}
