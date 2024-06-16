package com.example.sharedelement

import android.content.res.Configuration
import android.graphics.Bitmap.Config
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.sharedelement.ui.theme.SharedElementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    names: List<String> = List(20) { "$it" },
) {

    val orientation = LocalConfiguration.current.orientation
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isTablet =
        (orientation == Configuration.ORIENTATION_PORTRAIT && windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) || (orientation == Configuration.ORIENTATION_LANDSCAPE && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED)
    println("<<<<< - MainScreen : Orient:$orientation, Tab:$isTablet, W:${windowSizeClass.widthDp}, H:${windowSizeClass.heightDp}")

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val itemSelectionCallback = { index: Int -> selectedItemIndex = index }
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ItemHero(names, selectedItemIndex)
            ItemsList(names, selectedItemIndex, itemSelectionCallback)
        }
    }
}

@Composable
private fun ItemHero(
    names: List<String>,
    selectedItemIndex: Int,
) {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .aspectRatio(16f / 9f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            text = names[selectedItemIndex],
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium

        )
    }
}

@Composable
private fun ItemsList(
    names: List<String>,
    selectedItemIndex: Int,
    onClick: (Int) -> Unit,
) {

    LazyColumn {
        itemsIndexed(names) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(if (selectedItemIndex == index) Color.Red else Color.LightGray)
                    .clickable { onClick(index) },
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    text = item,
                    color = if (selectedItemIndex == index) Color.White else Color.DarkGray,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .height(1.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(Modifier)
}