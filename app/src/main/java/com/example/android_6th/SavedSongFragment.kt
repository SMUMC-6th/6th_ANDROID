package com.example.android_6th

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentLockerSavedsongBinding
import com.google.gson.Gson


class SavedSongFragment : Fragment() {

//    private val albumDatas = ArrayList<Album>()
//    lateinit var binding: FragmentSavedSongBinding
    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB: SongDatabase

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

//    private fun changeAlbumFragment(album: Album) {
//        (context as MainActivity).supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, AlbumFragment().apply {
//                arguments = Bundle().apply {
//                    val gson = Gson()
//                    val albumToJson = gson.toJson(album)
//                    putString("album", albumToJson)
//                }
//            })
//            .commitAllowingStateLoss()
//    }
}