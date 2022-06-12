package com.abhi41.jetfoodrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            JetFoodRecipeAppTheme {
             //   navController = rememberNavController()
                RootNavGraph(navController = rememberNavController())

            }
        }
    }
}

