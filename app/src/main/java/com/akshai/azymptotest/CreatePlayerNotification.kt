package com.akshai.azymptotest

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akshai.azymptotest.model.SongModel
import com.akshai.azymptotest.receiver.NotificationActionReceiver


class CreatePlayerNotification {


    companion object {
        val CHANNEL_ID = "channel1"
        val ACTION_PREVIUOS = "actionprevious"
        val ACTION_PLAY = "actionplay"
        val ACTION_NEXT = "actionnext"
    }


    private lateinit var notification: Notification


    fun createNotification(
        context: Context,
        track: SongModel,
        playButton: Int,
        pos: Int,
        size: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            val icon = BitmapFactory.decodeResource(context.resources, track.image)


            val flag =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE
                else 0


            val intentPrevious: Intent = Intent(context, NotificationActionReceiver::class.java)
                .setAction(ACTION_PREVIUOS)
            val pendingIntentPrevious: PendingIntent = PendingIntent.getBroadcast(
                context, 0,
                intentPrevious, flag)

            //*******************************************

            val intentPlay: Intent = Intent(context, NotificationActionReceiver::class.java)
                .setAction(ACTION_PLAY)

            val intentNext: Intent = Intent(context, NotificationActionReceiver::class.java)
                .setAction(ACTION_NEXT)

            //*******************************************

            val pendingIntentPlay = PendingIntent.getBroadcast(
                context, 0,
                intentPlay, flag)

            val pendingIntentNext: PendingIntent = PendingIntent.getBroadcast(
                context, 0,
                intentNext, flag)

            //*******************************************

            val drw_previous: Int = R.drawable.ic_media_previous
            val drw_next: Int = R.drawable.ic_media_next

            mediaSessionCompat.isActive = true
            //create notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_media_play)
                .setContentTitle(track.title)
                .setContentText(track.artist)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setShowWhen(false)
                .addAction(drw_previous, "Previous", pendingIntentPrevious)
                .addAction(playButton, "Play", pendingIntentPlay)
                .addAction(drw_next, "Next", pendingIntentNext)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification)
        }
    }
}