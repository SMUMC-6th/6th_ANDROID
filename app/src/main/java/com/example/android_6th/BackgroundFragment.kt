package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentBackgroundBinding
import com.example.android_6th.databinding.FragmentBannerBinding

class BackgroundFragment(val imgRes : Int) : Fragment() {

    lateinit var binding: FragmentBackgroundBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBackgroundBinding.inflate(inflater,container,false)
        binding.backgroundImageIv.setImageResource(imgRes)
        return binding.root
    }
}



