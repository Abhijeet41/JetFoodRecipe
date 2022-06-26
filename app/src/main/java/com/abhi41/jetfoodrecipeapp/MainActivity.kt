package com.abhi41.jetfoodrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.navigation.RootNavGraph
import com.abhi41.jetfoodrecipeapp.ui.theme.JetFoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

 //   private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isSystemInDarkTheme())
            {
                this.window.statusBarColor = ContextCompat.getColor(this,R.color.black)
            }else{
                this.window.statusBarColor = ContextCompat.getColor(this,R.color.colorPrimary)
            }

            JetFoodRecipeAppTheme {
             //   navController = rememberNavController()
                RootNavGraph(navController = rememberNavController())

            }
        }
    }
}

