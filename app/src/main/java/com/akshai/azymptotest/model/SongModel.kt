package com.akshai.azymptotest.model

import android.net.Uri
import android.os.Parcelable
import com.akshai.azymptotest.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongModel(
    var title: String = "",
    var songId: Int = 0,
    var artist: String = "Artist",
    var totalDuration: Int = 0,
    var currentPosition: Int = 0,
    val image : Int = R.drawable.ic_play,
    val uri : Uri? = null,
) : Parcelable