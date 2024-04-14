package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAadpter (fragment: Fragment) : FragmentStateAdapter(fragment){

    //여러 프래그먼트를 담을 공간?
    private val fragmentList : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1)
    }

}