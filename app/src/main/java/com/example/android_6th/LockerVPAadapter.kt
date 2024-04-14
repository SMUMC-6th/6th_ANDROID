package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAadapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavesongFragment()
            else -> SongfileFragment()
        }
    }
}