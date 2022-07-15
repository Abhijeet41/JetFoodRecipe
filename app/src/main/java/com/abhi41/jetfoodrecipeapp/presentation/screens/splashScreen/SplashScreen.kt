package com.abhi41.jetfoodrecipeapp.presentation.screens.splashScreen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.presentation.destinations.DashBoardScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
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
      //  navigator.popBackStack() //remove splash screen from stack
        navigator.navigate(DashBoardScreenDestination())
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