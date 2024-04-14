package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PannelVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val pannellist : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = pannellist.size

    override fun createFragment(position: Int): Fragment = pannellist[position]

    fun addPannel(fragment: Fragment) {
        pannellist.add(fragment)
        notifyItemInserted(pannellist.size-1)
    }

}