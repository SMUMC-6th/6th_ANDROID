package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentPannelBinding

class PannelFragment(val imgRes : Int) : Fragment() {
    lateinit var binding : FragmentPannelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelBinding.inflate(inflater, container, false)

        binding.pannelImageiv.setImageResource(imgRes) // 새로운 image resource 추가

        return binding.root
    }
}