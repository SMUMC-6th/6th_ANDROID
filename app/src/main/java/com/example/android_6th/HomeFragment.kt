package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val song = Song(binding.homeMainAlbumTitle1Tv.text.toString(), binding.homeMainAlbumSinger1Tv.text.toString())
        //fragment 전환 (fragment_home -> fragment_album)
        binding.homeMainAlbumSong1Iv.setOnClickListener{
            var albumfragment = AlbumFragment()
            var bundle = Bundle()
            bundle.putString("singer", song.singer)
            albumfragment.arguments = bundle
            //activity?.supportFragmentManager!!.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commit()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumfragment).commitAllowingStateLoss()
        }

        return binding.root
    }
}