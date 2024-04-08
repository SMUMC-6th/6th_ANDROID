package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment { //LockerFragment에 Tablayout 적용
        return when(position){
            0 -> SavedSongFragment()
            else -> MusicFileFragment()
        }
    }
}