package com.abhi41.jetfoodrecipeapp.utils

import android.app.Application
import androidx.multidex.MultiDex
import com.abhi41.jetfoodrecipeapp.utils.Constants.ONESIGNAL_APP_ID
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        // promptForPushNotifications will show the native Android notification permission prompt.
        OneSignal.promptForPushNotifications()
    }
}