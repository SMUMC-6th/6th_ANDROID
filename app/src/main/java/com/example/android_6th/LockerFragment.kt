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
    private var lockerDates = ArrayList<Locker>()

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



        return binding.root
    }
}