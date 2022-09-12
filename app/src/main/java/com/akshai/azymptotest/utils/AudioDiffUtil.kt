package com.akshai.azymptotest.utils

import androidx.recyclerview.widget.DiffUtil
import com.akshai.azymptotest.model.SongModel

class AudioDiffUtil(
    private val oldItem: ArrayList<SongModel>,
    private val newItem: ArrayList<SongModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItem.size
    }

    override fun getNewListSize(): Int {
        return newItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition].title == newItem[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldItem[oldItemPosition].title == newItem[newItemPosition].title -> false
            oldItem[oldItemPosition].songId == newItem[newItemPosition].songId -> false
            else -> true
        }
    }
}