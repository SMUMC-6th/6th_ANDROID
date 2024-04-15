package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding
    private val gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }

        // albumfragment에 viewpager 연결
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        //TabLayoutMediator: TabLayout을 viewpager2와 연결하는 중재자
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()


        /*binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
        }*/

        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
    }

}