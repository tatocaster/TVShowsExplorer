package me.tatocaster.letinterview.utils

import android.app.Activity
import com.tapadoo.alerter.Alerter
import me.tatocaster.letinterview.R


fun showErrorAlert(activity: Activity, title: String, message: String) {
    Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.errorBackground)
            .show()
}

fun showSuccessAlert(activity: Activity, message: String) {
    Alerter.create(activity)
            .setText(message)
            .setBackgroundColorRes(R.color.successBackground)
            .show()
}