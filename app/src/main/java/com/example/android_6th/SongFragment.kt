package com.example.android_6th

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentSongBinding

class SongFragment : Fragment(){

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.songMixoffTg.setOnClickListener{ songMixStatus(true) }
        binding.songMixonTg.setOnClickListener{ songMixStatus(false) }

        return binding.root
    }

    //내 취향 믹스 버튼 on/off 함수
    fun songMixStatus(mixOn : Boolean){
        if(mixOn){
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE

        }
        else{
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
    }
}