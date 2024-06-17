package com.example.android_6th

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import java.util.ArrayList
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding

    //ArrayList 선언
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*binding.homeAlbumImgIv1.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }*/


//        // 앨범 데이터 리스트 생성 더미 데이터
//        albumDatas.apply {
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
//        } //recyclerview에 들어갈 데이터 준비 완료
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져온다.
        Log.d("albumlist", albumDatas.toString())


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

            /*override fun onPlayAlbum(album: Album) {
                sendData(album)
            }*/


        })

        initBanner()
        initHomeViewPager()


        return binding.root
    }

    fun initBanner(){
        // banner
        // 리스트 안에 fragment 추가하기
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp3))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp4))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp5))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp6))

        // viewpager와 어댑터 연결
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //viewpager가 좌우로 스크롤됨

        // 추가 미션
        // viewpager indicator 애니메이션 동작 연결
        binding.dotsIndicator.setViewPager2(binding.homeBannerVp)
    }

    fun initHomeViewPager(){
        //메인homebanner
        val topBannerAdapter = BannerVPAdapter(this)
        topBannerAdapter.addFragment(HomeImageFragment(R.drawable.img_first_album_default))
        topBannerAdapter.addFragment(HomeImageFragment(R.drawable.img_second_album_default))
        topBannerAdapter.addFragment(HomeImageFragment(R.drawable.img_first_album_default))
        topBannerAdapter.addFragment(HomeImageFragment(R.drawable.img_second_album_default))

        // 어댑터 연결
        binding.homeViewPager.adapter = topBannerAdapter
        binding.homeViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelIndicator.setViewPager(binding.homeViewPager)
        startAutoSlide(topBannerAdapter)

    }


    //MainActivity에 album(데이터) 전달 //인터페이스 구현
    /*override fun sendData(album: Album){
        if (activity is MainActivity){
            val activity = activity as MainActivity
            activity.updateMainPlayerCL(album)
        }
    }*/

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

    // 일정 간격으로 슬라이드 변경 (3초마다)
    private fun startAutoSlide(adapter: BannerVPAdapter){
        timer.scheduleAtFixedRate(3000, 3000){
            handler.post {
                val nextItem = binding.homeViewPager.currentItem + 1
                if (nextItem < adapter.itemCount){
                    binding.homeViewPager.currentItem = nextItem
                }
                else{
                    binding.homeViewPager.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }
}


