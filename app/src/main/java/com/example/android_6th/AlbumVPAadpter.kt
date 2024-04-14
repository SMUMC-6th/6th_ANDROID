package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAadpter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3; // 3개의 프래그먼트 받음
    override fun createFragment(position: Int): Fragment { // 포지션에 따라 다른 화면을 보여준다.
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }

    }
}