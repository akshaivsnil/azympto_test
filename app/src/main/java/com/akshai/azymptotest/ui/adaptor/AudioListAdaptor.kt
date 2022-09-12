package com.akshai.azymptotest.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshai.azymptotest.databinding.LayoutItemAudioBinding
import com.akshai.azymptotest.model.SongModel
import com.akshai.azymptotest.utils.AudioDiffUtil
import javax.inject.Inject

class AudioListAdaptor @Inject constructor() :
    RecyclerView.Adapter<AudioListAdaptor.AudioViewHolder>() {

    private val audioList = ArrayList<SongModel>()
    private lateinit var itemListener: OnItemClickListener

    inner class AudioViewHolder(val binding: LayoutItemAudioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(audio: SongModel, position: Int) {
            binding.songModel = audio
            binding.card.setOnClickListener {
                itemListener.onItemClicked(position, audio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        return AudioViewHolder(
            LayoutItemAudioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.setData(audioList[position], position)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }


    fun setAudioItem(newAudios: ArrayList<SongModel>) {
        val diffCallback = AudioDiffUtil(audioList, newAudios)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        audioList.clear()
        audioList.addAll(newAudios)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setListener(listener: OnItemClickListener) {
        itemListener = listener
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, songModel: SongModel)
    }

}