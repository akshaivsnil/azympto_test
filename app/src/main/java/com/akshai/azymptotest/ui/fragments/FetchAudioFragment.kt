package com.akshai.azymptotest.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.akshai.azymptotest.R
import com.akshai.azymptotest.databinding.FragmentFetchAudioBinding
import com.akshai.azymptotest.model.SongModel
import com.akshai.azymptotest.ui.adaptor.AudioListAdaptor
import com.akshai.azymptotest.utils.hasStoragePermission
import com.akshai.azymptotest.utils.requestStoragePermission
import com.akshai.azymptotest.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FetchAudioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class FetchAudioFragment : Fragment(), AudioListAdaptor.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: FragmentFetchAudioBinding

    var mySongs: ArrayList<File> = arrayListOf()

    @Inject
    lateinit var audioLisAdapter: AudioListAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFetchAudioBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FetchAudioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FetchAudioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (requireContext().hasStoragePermission()) displayNames()
        else requireActivity().requestStoragePermission()


    }

    private fun findSongs(file: File): ArrayList<File> {
        val arrayFileList: ArrayList<File> = arrayListOf()
        val files = file.listFiles()

        return if (files != null) {
            for (singleFile in files) {
                if (singleFile.isDirectory && !singleFile.isHidden)
                    arrayFileList.addAll(findSongs(singleFile))
                else {
                    if (singleFile.name.endsWith(".mp3")
                        || singleFile.name.endsWith(".wav")
                    )
                        arrayFileList.add(singleFile)
                }
            }
            arrayFileList

        } else arrayFileList
    }

    private fun displayNames() {

        mySongs = findSongs(Environment.getExternalStorageDirectory())
        val items: ArrayList<SongModel> = arrayListOf()

        for (song in mySongs) {
            items.add(
                SongModel(
                    title = song.name.toString().replace(".mp3", "").replace(".wav", "")
                )
            )
        }
        binding.audioRecyclerView.adapter = audioLisAdapter
        audioLisAdapter.setAudioItem(items)
        audioLisAdapter.setListener(this@FetchAudioFragment)


    }

    override fun onItemClicked(position: Int, songModel: SongModel) {
        val bundle: Bundle = Bundle()
        bundle.putSerializable("mySongs", mySongs)
        bundle.putParcelable("song", songModel)
        bundle.putInt("position", position)
        this.findNavController().navigate(R.id.playerFragment, bundle)
    }

}
