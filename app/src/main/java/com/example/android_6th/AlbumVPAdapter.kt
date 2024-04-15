package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment { //position에 따라 각 fragment를 보여줌
        return when(position){
            0 -> SongFragment() //수록곡
            1 -> DetailFragment() // 상세정보
            else -> VideoFragment() // 영상
        }
    }
}