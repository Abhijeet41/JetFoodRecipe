package com.abhi41.jetfoodrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.presentation.NavGraphs
import com.abhi41.jetfoodrecipeapp.ui.theme.JetFoodRecipeAppTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import net.sqlcipher.database.SQLiteDatabase

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var appUpdate: AppUpdateManager? = null
    private val REQUEST_INAPPUPDATE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appUpdate = AppUpdateManagerFactory.create(this)
            SQLiteDatabase.loadLibs(this);
            checkInappUpdate()
            if (isSystemInDarkTheme()) {
                this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            } else {
                this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
            }

            JetFoodRecipeAppTheme {
                //   navController = rememberNavController()
                //RootNavGraph(navController = rememberNavController())
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = rememberNavController()
                )
            }
        }
    }

    fun checkInappUpdate() {
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_INAPPUPDATE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }
    fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_INAPPUPDATE)
            }
        }
    }
}

