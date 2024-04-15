package com.example.android_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) { //어댑터는 하나의 클래스를 상속 받아야 한다.

    // 하나의 리스트에 여러 개의 fragment를 담는다
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    // 클래스에서 연결된 viewpager에게 전달할 데이터 개수를 정하는 함수
    override fun getItemCount(): Int = fragmentlist.size //리스트 개수만큼 데이터 전달

    // fragment들을 생성해주는 함수
    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    // 이 함수가 처음 실행 시 fragmentlist에는 아무 값도 없으므로 homefragment에서 추가해줄 fragment를 써줌
    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment) //fragmentlist에 동작 값으로 받은 fragment를 추가 해준다.
        notifyItemInserted(fragmentlist.size-1) // fragmentlist 안에 새로운 값이 추가되었을 때 viewpager에게 알려줌.
    }
}