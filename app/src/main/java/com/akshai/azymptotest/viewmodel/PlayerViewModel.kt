package com.akshai.azymptotest.viewmodel

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshai.azymptotest.di.MainNotificationCompatBuilder
import com.akshai.azymptotest.di.SecondNotificationCompatBuilder
import com.akshai.azymptotest.model.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val notificationManager: NotificationManagerCompat,
    @MainNotificationCompatBuilder private val notificationBuilder: NotificationCompat.Builder,
    @SecondNotificationCompatBuilder private val notificationBuilder2: NotificationCompat.Builder,
) : ViewModel() {

    fun showPlayingNotification() {
        notificationManager.notify(
            2,
            notificationBuilder
                .setContentTitle("Hi")
                .build()
        )
    }


    fun showPlayerNotification(songModel: SongModel) {

        viewModelScope.launch {
            while (songModel.currentPosition != songModel.totalDuration) {
                delay(1000)
                notificationManager.notify(
                    1,
                    notificationBuilder2
                        .setContentTitle(songModel.title)
                        .setContentText("${songModel.currentPosition}/${songModel.totalDuration}")
                        .setProgress(songModel.totalDuration, songModel.currentPosition, false)
                        .build()
                )
                songModel.currentPosition += 1
            }
            notificationManager.cancel(1)
        }
    }

}