package com.example.android_6th

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.android_6th.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    var song = Song()

    private lateinit var songDB: SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // AlbumTable에 데이터 리스트 생성
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

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

        // home banner adapter 연결
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp)) // homeBanner fragment 추가
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //수평
        binding.homeBannerVp.setCurrentItem(0,true)
        binding.homeBannerDotsIndicator.setViewPager2(binding.homeBannerVp) //dotsIndicator 연결

        val pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addPannel(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addPannel(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addPannel(PannelFragment(R.drawable.img_first_album_default))
        binding.homePannelContentVp.adapter = pannelAdapter
        binding.homePannelContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //수평

        //pannel와 circle indicator 연결
        binding.homePannelIndicator.setViewPager(binding.homePannelContentVp)

        // 자동으로 다음 pannel로 슬라이드 되는 indicator 구현
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            val currentItem = binding.homePannelContentVp.currentItem //현재 보여지는 이미지
            val nextPage = (currentItem + 1) % pannelAdapter.itemCount //다음에 슬라이드 될 이미지
            binding.homePannelContentVp.setCurrentItem(nextPage, true)
        }
        val timer = Timer() // 2초 간격으로 주기적으로 작업을 실행
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000)



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
                    Log.d("앨범", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}