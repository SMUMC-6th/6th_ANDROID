package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한곡", "음악파일")

    /*//ArrayList 선언
    private var songDatas = ArrayList<Album>()*/


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this) //Tablayout 적용 (저장한곡, 음악파일)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        /*// 앨범 데이터 리스트 생성 더미 데이터
        songDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
            add(Album("Love me or Leave me", "DAY6 (데이식스)", R.drawable.img_album_exp7))
            add(Album("개화 (Flowering)", "LUCY", R.drawable.img_album_exp8))
            add(Album("Easy", "숀 (SHAUN)", R.drawable.img_album_exp9))
        } //recyclerview에 들어갈 데이터 준비 완료

        // 어뎁터와 데이터 리스트(더미데이터) 연결
        val songRVAdapter = SongRVAdapter(songDatas)

        // 리사이클러뷰에 어뎁터를 연결
        //binding.lockerContentVp.adapter = songRVAdapter
        binding.lockerContentVp.adapter = songRVAdapter

        // 레이아웃 매니저 설정
        binding.lockerContentVp.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        // Listener 객체를 어뎁터에게 던져줌 (리사이클러뷰의 아이템을 클릭했을 때 AlbumFragment로 이동)
        songRVAdapter.setMyItemClickListener(object: SongRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                TODO("Not yet implemented")
            }

            override fun onRemoveSong(position: Int) {
                songRVAdapter.removeItem(position)
            }

        })*/
        return binding.root
    }

}