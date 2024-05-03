package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentAlbumBinding
import com.example.flo.SongDatabase
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    private var gson: Gson = Gson()
    lateinit var binding : FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보","영상")

    //좋아요 눌렀음? 좋아요 누름 채워진 하트
    private var isliked: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
        isliked = islikedAlbum(album.id) // 초기 설정
        setInit(album)
        setOnClickListeners(album)
        //album은 현재 저장된 값

        binding.albumBackIv.setOnClickListener {

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()

        }

        val albumAdapter = AlbumVPAadpter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album?) {
        binding.albumAlbumIv.setImageResource(album?.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()

        if(isliked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }



    }

    //좋아요 눌렀냐?
    private fun getJwt(): Int {//?는 프래그먼트에서 쓰는 방법?
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt",0)
        //sharedpreference에 값이 없음 0을 반환
    }

    //좋아요 누르면 DB에 저장
    private fun likeAlbum(userId : Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)//좋아요 누르면 라이크 테비르에 정보 추가

        songDB.albumDao().likeAlbum(like)
    }

    //좋아요 눌렀냐
    private  fun islikedAlbum(albumId: Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId,albumId)

        return likeId != null // 좋아요 누르면 트루, 아님 폴스
    }

    //좋아요 해제
    private  fun dislikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId,albumId)


    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isliked){//좋아요 이미 눌러져 있을 때 좋아요 누르면 취소겠지
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                dislikedAlbum(album.id)
            }
            else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }

}