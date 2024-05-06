package com.example.android_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    private val gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false) //초기화

        // arguments에서 데이터 꺼내서 Binding
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        isLiked = isLikedAlbum(album.id)
        setInit(album) //데이터 Binding
        setOnClickListeners(album)

        // HomeFragment 전환
        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        // album viewpager adapter 연결 및 초기화
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){ // tab item과 viewpage fragment 연결
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        if (isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    // 현재 Jwt값이 저장 되어 있는지 확인하는 함수 (로그인한 사용자 확인 함수)
    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    private fun likeAlbum(userId : Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!

        val like = Like(userId, albumId) //좋아요 누른 앨범 -> LikeTable에 저장
        songDB.albumDao().likeAlbum(like)
    }

    // 앨범별 좋아요 클릭 여부를 확인하는 함수
    private fun isLikedAlbum(albumId: Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt() //user 식별

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId) // 어떤 사용자가 해당 앨범에 좋아요를 눌렀는지 확인해주는 변수

        return likeId != null
    }

    // 좋아요 누른 앨범을 해제 및 삭제하는 함수
    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt() //user 식별

        songDB.albumDao().disLikedAlbum(userId, albumId) // LikeTable에서 삭제
    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()

        binding.albumLikeIv.setOnClickListener {
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id) //함수호출
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id) //함수호출
            }
        }
    }
}