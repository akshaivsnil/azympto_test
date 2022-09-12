package com.akshai.azymptotest.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.sendBroadcast(
            Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.action)
        );
    }
}