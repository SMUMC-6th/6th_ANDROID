package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentLockerSavedsongBinding

class LockerSavedsongFragment : Fragment() {

    lateinit var binding: FragmentLockerSavedsongBinding
    private var lockerDatas = ArrayList<Locker>()
    lateinit var songDB: SongDatabase

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
        binding.lockerPlaylistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = LockerRVAdapter()

        songRVAdapter.setMyItemClickListener(object : LockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                //DB에서 제거
                songDB.songDao().updateIsLikeById(false,songId)
            }

        })

        binding.lockerPlaylistRv.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

}