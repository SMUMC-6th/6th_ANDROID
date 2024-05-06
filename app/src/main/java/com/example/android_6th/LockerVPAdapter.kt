package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LockerSavedsongFragment() // 저장한 곡 fragment
            1 -> LockerMusicfileFragment() // 음악파일 fragment
            else -> LockerSavedAlbumFragment() // 저장한 앨범 fragment
        }
    }
}