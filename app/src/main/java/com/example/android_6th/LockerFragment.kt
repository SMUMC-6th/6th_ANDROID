package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_6th.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private var lockerDates = ArrayList<Locker>()
    private var information = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        lockerDates.apply {
            add(Locker("제육볶음","아이유",R.drawable.img_album_exp))
            add(Locker("치킨","치킨집 사장님",R.drawable.img_album_exp2))
            add(Locker("피자","미스터피자",R.drawable.img_album_exp3))
            add(Locker("탕수육","중국집",R.drawable.img_album_exp4))
            add(Locker("비타500","롯데",R.drawable.img_album_exp5))
            add(Locker("치약","민트초코",R.drawable.img_album_exp6))
            add(Locker("제육볶음","아이유",R.drawable.img_album_exp))
            add(Locker("치킨","치킨집 사장님",R.drawable.img_album_exp2))
            add(Locker("피자","미스터피자",R.drawable.img_album_exp3))
            add(Locker("탕수육","중국집",R.drawable.img_album_exp4))
            add(Locker("비타500","롯데",R.drawable.img_album_exp5))
            add(Locker("치약","민트초코",R.drawable.img_album_exp6))
            add(Locker("제육볶음","아이유",R.drawable.img_album_exp))
            add(Locker("치킨","치킨집 사장님",R.drawable.img_album_exp2))
            add(Locker("피자","미스터피자",R.drawable.img_album_exp3))
            add(Locker("탕수육","중국집",R.drawable.img_album_exp4))
            add(Locker("비타500","롯데",R.drawable.img_album_exp5))
            add(Locker("치약","민트초코",R.drawable.img_album_exp6))

        }


//        리사이클러 뷰
        val lockerRVAdapter = LockerRVAdapter(lockerDates)
       binding.homeLockerMusicAlbumIv.adapter = lockerRVAdapter
        binding.homeLockerMusicAlbumIv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        lockerRVAdapter.setMyItemClickListener(object: LockerRVAdapter.MyItemClickListener{
            override fun onItemClick() {

            }

            override fun onRemoveLocker(position: Int) {
                lockerRVAdapter.removeItem(position)
            }

        })

        val  lockerAdapter = LockerVPAadapter(this)
        binding.lockerBannerVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerBannerVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
        //jwp 받아서 sharedpreference에 값이 들어가 있는지 확인
        //헤서 로그인, 로그안웃 버튼 만듬.


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }


    //jwt의 값에 따란 로그인/로그아웃 판다
    private fun initViews(){
        val jwt : Int = getJwt()
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }

        }
        else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                //로그아웃 진행
                logout()
                startActivity(Intent(activity,MainActivity::class.java))
            }

        }
    }


    private fun getJwt(): Int {//?는 프래그먼트에서 쓰는 방법?
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt",0)
        //sharedpreference에 값이 없음 0을 반환
    }

    private fun logout() {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("jwt")
        editor.apply()


}
}