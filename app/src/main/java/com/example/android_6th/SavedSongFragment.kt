package com.example.android_6th

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentLockerSavedsongBinding
import com.google.gson.Gson


class SavedSongFragment : Fragment() {

//    private val albumDatas = ArrayList<Album>()
//    lateinit var binding: FragmentSavedSongBinding
    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB: SongDatabase

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

//        // 보관함 노래 데이터 리스트 생성 더미 데이터
//        albumDatas.apply {
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
//            add(Album("Love me or Leave me", "DAY6 (데이식스)", R.drawable.img_album_exp7))
//            add(Album("개화 (Flowering)", "LUCY", R.drawable.img_album_exp8))
//            add(Album("Easy", "숀 (SHAUN)", R.drawable.img_album_exp9))
//        }

//        //어뎁터와 데이터 리스트(더미데이터) 연결
//        val lockerAlbumRVAdapter = LockerAlbumRVAdapter(albumDatas)
//
//        // 리사이클러뷰에 어뎁터를 연결
//        binding.lockerMusicAlbumRv.adapter = lockerAlbumRVAdapter
//
//        // 레이아웃 매니저 설정
//        binding.lockerMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())
//
//        // Listener 객체를 어뎁터에게 던져줌
//        lockerAlbumRVAdapter.setItemClickListener(object : LockerAlbumRVAdapter.OnItemClickListener {
//            override fun onItemClick(album: Album) {
//                changeAlbumFragment(album)
//            }
//
//            override fun onRemoveAlbum(position: Int) {
//                lockerAlbumRVAdapter.removeItem(position)
//            }
//        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }

        })

        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

//    private fun changeAlbumFragment(album: Album) {
//        (context as MainActivity).supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, AlbumFragment().apply {
//                arguments = Bundle().apply {
//                    val gson = Gson()
//                    val albumToJson = gson.toJson(album)
//                    putString("album", albumToJson)
//                }
//            })
//            .commitAllowingStateLoss()
//    }
}