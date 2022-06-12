package com.abhi41.jetfoodrecipeapp.presentation.screens.splashScreen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.navigation.Graph
import com.abhi41.jetfoodrecipeapp.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    var alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(3000)
        navController.popBackStack() //remove splash screen from stack
        navController.navigate(Graph.DASHBOARD)
    }
    AnimatedSplashScreen(alpha = alphaAnim.value)
}

@Composable
fun AnimatedSplashScreen(alpha: Float) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = "splash_screen"
        )
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    AnimatedSplashScreen(0f)
}