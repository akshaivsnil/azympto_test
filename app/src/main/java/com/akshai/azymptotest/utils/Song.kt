package com.akshai.azymptotest.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
        val title: String,
        val trackNumber: Int,
        val year: Int,
        val duration: Int,
        val path: String?,
        val albumName: String,
        val artistId: Int,
        val artistName: String) : Parcelable

