package com.abhi41.jetfoodrecipeapp.utils

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun ShowFeedbackDialog(context: Context, activity: Activity) {
    val reviewManager = ReviewManagerFactory.create(context)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewManager.launchReviewFlow(activity, it.result)
        }
    }
}