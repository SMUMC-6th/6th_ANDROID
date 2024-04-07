package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.ActivityMainBinding
import com.example.android_6th.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    var song = Song()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("밤양갱", "비비 (BIBI)", R.drawable.img_album_exp3))
            add(Album("EASY", "LE SSERAFIM", R.drawable.img_album_exp4))
            add(Album("I AM", "IVE (아이브)", R.drawable.img_album_exp5))
            add(Album("Talk Saxy", "RIIZE", R.drawable.img_album_exp6))
            add(Album("Drama", "aespa", R.drawable.img_album_exp7))
            add(Album("To. X", "TAEYEON", R.drawable.img_album_exp8))
        }

        // Adapter와 Datalist 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Listener 객체 던져주기
        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            //fragment 전환 (fragment_home -> fragment_album)
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            // main mini player 가수&제목 변경
            override fun onPlayerClick(position: Int) {
                albumRVAdapter.changeMiniPlayer(position)
            }
        })

        return binding.root
    }

    // Homefragment -> AlbumFragment 전환 함수
    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}