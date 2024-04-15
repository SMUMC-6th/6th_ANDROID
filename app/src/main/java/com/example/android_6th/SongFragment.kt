package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentDetailBinding
import com.example.android_6th.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding : FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)


        // 내 취향 버튼 클릭 이벤트
        binding.songMixonTg.setOnClickListener {
            setMixStatus(false)
        }
        binding.songMixoffTg.setOnClickListener {
            setMixStatus(true)
        }


        return binding.root
    }

    // 내 취향 버튼 클릭 이벤트 함수
    fun setMixStatus(mixOn: Boolean){
        if(mixOn){
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE
        }
        else {
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
    }
}