package com.example.android_6th

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.android_6th.databinding.FragmentHomeBinding
import com.example.flo.SongDatabase
import com.google.gson.Gson
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDates = ArrayList<Album>()
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var songDB : SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*binding.homePannelElbum1Iv.setOnClickListener {

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }*/

        //DB 값 넣기
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDates.addAll(songDB.albumDao().getAlbumes())





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

        val bannerAdaper = BannerVPAadpter(this)
        bannerAdaper.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdaper.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))


        binding.homeBannerVp.adapter = bannerAdaper
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //뷰페이저
        val backgroundAdapter = BackgroundVPAadpter(this)
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_first_album_default))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_album_exp))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_album_exp))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_album_exp))

        binding.homePannelBackgroundVp.adapter = backgroundAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        startAutoSlide(backgroundAdapter)


        return binding.root
    }

    private fun startAutoSlide(adapter: BackgroundVPAadpter) {
        timer.scheduleAtFixedRate(3000, 3000) {
            handler.post {
                val nextItem = binding.homePannelBackgroundVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.homePannelBackgroundVp.currentItem = nextItem
                } else {
                    binding.homePannelBackgroundVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }



}