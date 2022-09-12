package com.akshai.azymptotest.utils


class Utils {
    companion object {
        fun createTime(duration: Int): String {
            var time: String = ""
            val min = duration / 1000 / 60
            val sec = duration / 1000 % 60
            time += "$min:"
            if (sec < 10) time += "0"
            time += sec
            return time
        }

    }
}
