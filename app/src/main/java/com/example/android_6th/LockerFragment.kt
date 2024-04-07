package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private var lockerDatas = ArrayList<Locker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        // lockerDatas 더미 데이터 입력
        lockerDatas.apply{
            add(Locker("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Locker("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Locker("밤양갱", "비비 (BIBI)", R.drawable.img_album_exp3))
            add(Locker("EASY", "LE SSERAFIM", R.drawable.img_album_exp4))
            add(Locker("I AM", "IVE (아이브)", R.drawable.img_album_exp5))
            add(Locker("Talk Saxy", "RIIZE", R.drawable.img_album_exp6))
            add(Locker("Drama", "aespa", R.drawable.img_album_exp7))
            add(Locker("To. X", "TAEYEON", R.drawable.img_album_exp8))
        }

        // Adapter와 Datalist 연결
        val lockerRVAdapter = LockerRVAdapter(lockerDatas)
        binding.lockerPlaylistRv.adapter = lockerRVAdapter
        binding.lockerPlaylistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Listener 객체 던져주기
        lockerRVAdapter.setMyItemClickListener(object: LockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(position: Int) {
                lockerRVAdapter.removeItem(position)
            }
        })

        return binding.root
    }
}