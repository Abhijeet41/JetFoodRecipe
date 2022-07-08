package com.abhi41.jetfoodrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.abhi41.jetfoodrecipeapp.data.local.database.RecipesDatabase
import com.abhi41.jetfoodrecipeapp.navigation.RootNavGraph
import com.abhi41.jetfoodrecipeapp.ui.theme.JetFoodRecipeAppTheme
import com.abhi41.jetfoodrecipeapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import net.sqlcipher.database.SQLiteDatabase

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

 //   private lateinit var navController: NavHostController
   lateinit var db :RecipesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SQLiteDatabase.loadLibs(this);
             //if it's singleton (and i suggest making it as such)
            /*val databasePath = getDatabasePath(Constants.DATABASE_NAME);
            val database = SQLiteDatabase.openDatabase(databasePath.absolutePath, "password",
                null, SQLiteDatabase.OPEN_READWRITE, null, null)*/

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

