package com.example.android_6th

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.ArrayList

class HomeFragment : Fragment(), CommunicationInterface {


    lateinit var binding: FragmentHomeBinding

    //ArrayList 선언
    private var albumDatas = ArrayList<Album>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*binding.homeAlbumImgIv1.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }*/


        // 앨범 데이터 리스트 생성 더미 데이터
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        } //recyclerview에 들어갈 데이터 준비 완료


        //어뎁터와 데이터 리스트(더미데이터) 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)

        // 리사이클러뷰에 어뎁터를 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter

        // 레이아웃 매니저 설정 // LinearLayout: 수평(horizontal)으로 설정
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        // Listener 객체를 어뎁터에게 던져줌 (리사이클러뷰의 아이템을 클릭했을 때 AlbumFragment로 이동)
        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{

            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }

            /*override fun onPlayButtonClick(album: Album){
                val gson = Gson()
                var albumJson = gson.toJson(album)

                val prefs: SharedPreferences = context!!.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

                val editor = prefs.edit()

                editor.putString("miniPlaySongInfo", albumJson)
                editor.apply()
                mainActivity.initPlayList()
                mainActivity.initSong()
            }*/

        })



        return binding.root
    }

    //MainActivity에 album(데이터) 전달 //인터페이스 구현
    override fun sendData(album: Album){
        if (activity is MainActivity){
            val activity = activity as MainActivity
            activity.updateMainPlayerCL(album)
        }
    }

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


