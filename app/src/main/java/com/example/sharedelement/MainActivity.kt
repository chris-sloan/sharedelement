package com.example.sharedelement

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.layoutId
import com.example.sharedelement.ui.theme.SharedElementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        println("<<<<< - onCreate : ")
        setContent {
            SharedElementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onMaximised = { isMaximised, isTablet ->
                            println("<<<<< - onMaximisedCallback : $isMaximised, $isTablet, ${resources.configuration.orientation}")
                            requestedOrientation = if (isMaximised) {
                                println("<<<<< - Forcing Landscape : ")
                                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                            } else {
                                println("<<<<< - Forcing Unspecified : ")
                                if (isTablet) {
                                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                } else {
                                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        println("<<<<< - onConfigurationChanged : 1 - ${resources.configuration.orientation}, ${newConfig.orientation}")
        super.onConfigurationChanged(newConfig)
        println("<<<<< - onConfigurationChanged : 2 - ${resources.configuration.orientation}, ${newConfig.orientation}")
    }

    override fun onPause() {
        super.onPause()
        println("<<<<< - onPause : ")
    }

    override fun onResume() {
        super.onResume()
        println("<<<<< - onResume : ")
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    names: List<String> = List(20) { "$it" },
    onMaximised: (Boolean, Boolean) -> Unit,
) {
    var isMaximised by remember { mutableStateOf(false) }
    val orientation = LocalConfiguration.current.orientation
    val windowInfo = rememberWindowInfo()
    val isTablet = windowInfo.isTablet

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    val maximise: (Boolean?) -> Unit = { max ->
        println("<<<<< - MainScreen : maximise 1: Max:$max, isMax:$isMaximised")
        val shouldMax = max ?: !isMaximised
        println("<<<<< - MainScreen : maximise 2: Max:$max, ShouldMax:$shouldMax")
        onMaximised(shouldMax, isTablet)
        isMaximised = shouldMax
    }

    val itemSelectionCallback = { index: Int ->
        println("<<<<< - MainScreen : itemSelection : $index")
        selectedItemIndex = index
        maximise(true)
    }

    val constraints = remember(isMaximised, orientation) {
        println("<<<<< - MainScreen : constraints : $isMaximised, $orientation")
        if (isMaximised) {
            println("<<<<< - ConstraintSet for MAXIMISED : ")
            ConstraintSet {
                val hero = createRefFor("hero")
                val list = createRefFor("list")
                val info = createRefFor("info")
                constrain(hero) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = fillToConstraints
                }
                constrain(list) {
                    visibility = Gone
                }
                constrain(info) {
                    visibility = Gone
                }
            }
        } else if (isTablet && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            println("<<<<< - ConstraintSet for LANDSCAPE : ")
            ConstraintSet {
                val hero = createRefFor("hero")
                val list = createRefFor("list")
                val info = createRefFor("info")
                constrain(hero) {
                    top.linkTo(parent.top)
                    start.linkTo(list.end)
                    end.linkTo(parent.end)
                    width = Dimension.ratio("16:9")
                    height = fillToConstraints

                }
                constrain(list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(hero.start)
                    bottom.linkTo(parent.bottom)
                    width = fillToConstraints
                    height = fillToConstraints
                }
                constrain(info) {
                    top.linkTo(hero.bottom)
                    start.linkTo(list.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = fillToConstraints
                    height = fillToConstraints
                }
            }
        } else {
            println("<<<<< - MainScreen : constraint set for everything else - portrait")
            ConstraintSet {
                val hero = createRefFor("hero")
                val list = createRefFor("list")
                val info = createRefFor("info")
                constrain(hero) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = fillToConstraints
                }
                constrain(list) {
                    top.linkTo(hero.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = fillToConstraints
                    height = fillToConstraints
                }
                constrain(info) {
                    visibility = Gone
                }
            }

        }
    }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue),
        constraintSet = constraints,
        animateChanges = true
    ) {
        ItemHero(
            modifier = Modifier.layoutId("hero"),
            names = names,
            selectedItemIndex = selectedItemIndex,
            onClick = { maximise(null) }
        )
        ItemsList(
            modifier = Modifier.layoutId("list"),
            names = names,
            selectedItemIndex = selectedItemIndex,
            onClick = itemSelectionCallback
        )
        HeroInfo(
            modifier = Modifier.layoutId("info")
        )
    }
}


@Composable
private fun ItemHero(
    modifier: Modifier,
    names: List<String>,
    selectedItemIndex: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Red)
            .aspectRatio(16f / 9f)
            .clickable { onClick() },
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
    modifier: Modifier,
    names: List<String>,
    selectedItemIndex: Int,
    onClick: (Int) -> Unit,
) {

    LazyColumn(modifier) {
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
private fun HeroInfo(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color.Green)
    )
}


@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(
        modifier = Modifier,
        onMaximised = { _, _ -> }
    )
}