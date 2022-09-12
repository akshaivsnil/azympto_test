package com.akshai.azymptotest.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akshai.azymptotest.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {


    @Singleton
    @Provides
    @MainNotificationCompatBuilder
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {

        return NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("Spotify")
            .setContentText("Welcome")
            .setSmallIcon(R.drawable.ic_play)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }


    @Singleton
    @Provides
    @SecondNotificationCompatBuilder
    fun provideSecondNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Second Channel ID")
            .setSmallIcon(R.drawable.ic_play)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {

        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Spotify Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )


            val channel2 = NotificationChannel(
                "Second Channel ID",
                "Second Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channel2)
        }
        return notificationManager
    }


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainNotificationCompatBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecondNotificationCompatBuilder