package com.akshai.azymptotest.ui.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.akshai.azymptotest.CreatePlayerNotification
import com.akshai.azymptotest.Playable
import com.akshai.azymptotest.R
import com.akshai.azymptotest.databinding.FragmentPlayerBinding
import com.akshai.azymptotest.model.SongModel
import com.akshai.azymptotest.receiver.OnClearFromRecentService
import com.akshai.azymptotest.utils.Utils
import com.akshai.azymptotest.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class PlayerFragment : Fragment(), Playable {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = "PlayerFragment"


    private lateinit var binding: FragmentPlayerBinding
    private val viewModel: PlayerViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var updateSeeker: Thread
    private lateinit var updateTime: Thread

    var mySongs: ArrayList<File> = arrayListOf()
    var allTracks: ArrayList<SongModel> = arrayListOf()
    var songModel: SongModel = SongModel()
    var songPosition: Int = 0
    var position: Int = 0
    var isPlaying = false
    private lateinit var notificationManager: NotificationManager

    private var mAudioManager: AudioManager? = null


    private var mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
    private var mPlayOnFocusGain: Boolean = false

    private val mOnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> mCurrentAudioFocusState = AUDIO_FOCUSED
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                    // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                    mCurrentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    // Lost audio focus, but will gain it back (shortly), so note whether
                    // playback should resume
                    mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
                    mPlayOnFocusGain = isMediaPlayer()
                }
                AudioManager.AUDIOFOCUS_LOSS ->
                    // Lost audio focus, probably "permanently"
                    mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
            }

            if (mediaPlayer != null) {
                // Update the player state based on the change
                configurePlayerState()
            }
        }

    private fun isMediaPlayer(): Boolean {
        return if (mediaPlayer != null)
            mediaPlayer!!.isPlaying
        else false
    }


    private fun configurePlayerState() {

        if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_NO_DUCK) {
            // We don't have audio focus and can't duck, so we have to pause
            onTrackPause()
        } else {

            if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_CAN_DUCK) {
                // We're permitted to play, but only if we 'duck', ie: play softly
                mediaPlayer!!.setVolume(VOLUME_DUCK, VOLUME_DUCK)
            } else {
                mediaPlayer!!.setVolume(VOLUME_NORMAL, VOLUME_NORMAL)
            }

            // If we were playing when we lost focus, we need to resume playing.
            if (mPlayOnFocusGain) {
                mediaPlayer!!.start()
                mPlayOnFocusGain = false
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mySongs = it.getSerializable("mySongs") as ArrayList<File>
            allTracks = getAllTracks(mySongs)
            songModel = it.getParcelable<SongModel>("song")!!
            songPosition = it.getInt("position")
            position = songPosition
        }

        mAudioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        giveUpAudioFocus()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            requireContext().apply {
                registerReceiver(broadcastReceiver, IntentFilter("TRACKS_TRACKS"))
                startService(Intent(this, OnClearFromRecentService::class.java))
            }

        }

    }

    private fun tryToGetAudioFocus() {

        val result = mAudioManager!!.requestAudioFocus(
            mOnAudioFocusChangeListener,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_FOCUSED
        } else {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }


    private fun giveUpAudioFocus() {
        if (mAudioManager!!.abandonAudioFocus(mOnAudioFocusChangeListener) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreatePlayerNotification.CHANNEL_ID,
                "Music Player", NotificationManager.IMPORTANCE_LOW
            )
            notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }
    }


    private fun getAllTracks(mySongs: ArrayList<File>): ArrayList<SongModel> {
        val list = arrayListOf<SongModel>()
        mySongs.forEachIndexed { index, song ->
            list.add(
                SongModel(
                    title = song.name.toString().replace(".mp3", "").replace(".wav", ""),
                    uri = Uri.parse(song.toString())
                )
            )
        }
        return list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {

        // The volume we set the media player to when we lose audio focus, but are
        // allowed to reduce the volume instead of stopping playback.
        private val VOLUME_DUCK = 0.2f

        // The volume we set the media player when we have audio focus.
        private val VOLUME_NORMAL = 1.0f

        // we don't have audio focus, and can't duck (play at a low volume)
        private val AUDIO_NO_FOCUS_NO_DUCK = 0

        // we don't have focus, but can duck (play at a low volume)
        private val AUDIO_NO_FOCUS_CAN_DUCK = 1

        // we have full audio focus
        private val AUDIO_FOCUSED = 2


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMusicPlayer(allTracks, position)

        binding.imageView.setOnClickListener {
            viewModel.showPlayingNotification()
        }


        binding.seekBar.apply {
            max = mediaPlayer!!.duration
            progressDrawable.setColorFilter(
                resources.getColor(R.color.purple_200),
                PorterDuff.Mode.MULTIPLY
            )
            thumb.setColorFilter(resources.getColor(R.color.purple_200), PorterDuff.Mode.SRC_IN)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, p1: Int, p2: Boolean) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mediaPlayer?.seekTo(seekBar.progress)
                }

            })
        }

        binding.playPauseBtn.setOnClickListener { btn ->
            mediaPlayer?.let {
                if (it.isPlaying) {
                    btn.setBackgroundResource(R.drawable.ic_pause)
                    it.pause()
                    onTrackPause()
                } else {
                    btn.setBackgroundResource(R.drawable.ic_play)
                    it.start()
                    onTrackPlay()
                }
            }
        }

        binding.playNextBtn.setOnClickListener {
            onTrackNext()
        }

        binding.playPrevBtn.setOnClickListener {
            onTrackPrevious()
        }

        mediaPlayer?.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(p0: MediaPlayer?) {
                onTrackNext()
            }
        })

    }

    private fun initMusicPlayer(allTracks: ArrayList<SongModel>, position: Int) {
        tryToGetAudioFocus()
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        mediaPlayer = MediaPlayer.create(requireContext(), allTracks[position].uri).apply {
            start()
            onTrackStart()
        }
        binding.playPauseBtn.setBackgroundResource(R.drawable.ic_pause)
        binding.songName.text = songModel.title
        binding.songName.isSelected = true
        binding.seekBar.max = mediaPlayer!!.duration
        binding.stopTimer.text = Utils.createTime(mediaPlayer!!.duration)


        lifecycleScope.launch {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.let { player ->
                    val totalDuration: Int = player.duration
                    var currentPosition: Int = 0
                    while (currentPosition < totalDuration) {
                        try {
                            delay(1000)
                            currentPosition = getPlayerCurrentPosition()
                            binding.seekBar.progress = currentPosition
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentTime: String = Utils.createTime(mediaPlayer!!.currentPosition)
                binding.startTimer.text = currentTime
                handler.postDelayed({ this.run() }, 1000)
            }
        }, 1000)

    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.extras!!.getString("actionname")) {
                CreatePlayerNotification.ACTION_PREVIUOS -> onTrackPrevious()
                CreatePlayerNotification.ACTION_PLAY -> if (isPlaying) {
                    onTrackPause()
                } else {
                    onTrackPlay()
                }
                CreatePlayerNotification.ACTION_NEXT -> onTrackNext()
            }
        }
    }

    private suspend fun getPlayerCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    override fun onTrackPrevious() {
        if (position != 0) {
            position--
            initMusicPlayer(allTracks, position)
            CreatePlayerNotification().createNotification(
                requireContext(), allTracks[position],
                R.drawable.ic_pause, position, allTracks.size - 1
            )
            binding.songName.text = allTracks[position].title
        }

    }

    override fun onTrackStart() {
        CreatePlayerNotification().createNotification(
            requireContext(), allTracks[position],
            R.drawable.ic_pause, position, allTracks.size - 1
        )
        binding.playPauseBtn.setImageResource(R.drawable.ic_pause);
        binding.songName.text = allTracks[position].title
        isPlaying = true
    }

    override fun onTrackPlay() {
        tryToGetAudioFocus()
        mediaPlayer?.start()
        CreatePlayerNotification().createNotification(
            requireContext(), allTracks[position],
            R.drawable.ic_pause, position, allTracks.size - 1
        )
        binding.playPauseBtn.setImageResource(R.drawable.ic_pause);
        binding.songName.text = allTracks[position].title
        isPlaying = true
    }

    override fun onTrackPause() {
        mediaPlayer?.pause()
        CreatePlayerNotification().createNotification(
            requireContext(), allTracks[position],
            R.drawable.ic_play, position, allTracks.size - 1
        );
        binding.playPauseBtn.setImageResource(R.drawable.ic_play);
        binding.songName.text = allTracks[position].title
        isPlaying = false;
    }

    override fun onTrackNext() {
        if (position != allTracks.size - 1) {
            position++
            initMusicPlayer(allTracks, position)
            CreatePlayerNotification().createNotification(
                requireContext(), allTracks[position],
                R.drawable.ic_pause, position, allTracks.size - 1
            )
            binding.songName.text = allTracks[position].title
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll()
        }
        requireContext().unregisterReceiver(broadcastReceiver)
    }


}