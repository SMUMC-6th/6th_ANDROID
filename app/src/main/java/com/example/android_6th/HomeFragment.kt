package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentHomeBinding
import com.google.gson.Gson
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDates = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*binding.homePannelElbum1Iv.setOnClickListener {

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }*/

        albumDates.apply{
            add(Album("Butter","방탄소년단",R.drawable.img_album_exp))
            add(Album("electronis","전자기학",R.drawable.img_album_exp2))
            add(Album("psycho","심리학",R.drawable.img_album_exp3))
            add(Album("suncream","햇빛",R.drawable.img_album_exp4))
            add(Album("volt","충전기",R.drawable.img_album_exp5))
            add(Album("bottle","물병",R.drawable.img_album_exp6))
        }




        //어댑터와 데이터 리스트 연결
        val albumRVAdapter = AlbumRVAdapter(albumDates)
        //리사이클뷰에 어댑터를 연결
        binding.homeTodayMusicAlbumIv.adapter = albumRVAdapter
        //레이아웃 매니저 설정
        binding.homeTodayMusicAlbumIv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        //프래그먼트에서 어댑터?
        albumRVAdapter.setMyitemClickListener(object:AlbumRVAdapter.MyitemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
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

        })

        return binding.root
    }
}