package com.abhi41.jetfoodrecipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeapp.presentation.NavGraphs
import com.abhi41.jetfoodrecipeapp.ui.theme.JetFoodRecipeAppTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import net.sqlcipher.database.SQLiteDatabase
import java.lang.reflect.InvocationTargetException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private var appUpdate: AppUpdateManager? = null
    private val REQUEST_INAPPUPDATE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appUpdate = AppUpdateManagerFactory.create(this)
            SQLiteDatabase.loadLibs(this)

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    Log.e(TAG, "Token failed to receive")
                    return@addOnCompleteListener
                }
                val token = task.getResult()
                Log.d(TAG, "Token $token")
        //dOhKFh6JT2ufPWH3fZi26D:APA91bH4p1S0GBeToF2UInr8gDy5l0FZ3G9GyqOayhVL4tjYkOAHP-BwKWHcdRQ3Om3oLWjrBFVEydjkO0WE5mNOb1QdKTeaQSPhPaMSJdACkF1loUJFGr0_NsfnarnvT7SKKKp1_A4G
            }

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
                try {
                    appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_INAPPUPDATE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }catch (e: InvocationTargetException){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_INAPPUPDATE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                    applicationContext,
                    "Update canceled by user! Result Code: $resultCode", Toast.LENGTH_LONG
                ).show()
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(
                    applicationContext,
                    "Update success! Result Code: $resultCode", Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update Failed! Result Code: $resultCode",
                    Toast.LENGTH_LONG
                ).show()
                checkInappUpdate()
            }
        }
    }
    fun inProgressUpdate(){
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdate?.startUpdateFlowForResult(updateInfo,AppUpdateType.IMMEDIATE,this,REQUEST_INAPPUPDATE)
            }
        }
    }



}

