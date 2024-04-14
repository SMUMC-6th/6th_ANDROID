package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.FragmentSavesongBinding
import com.example.android_6th.databinding.FragmentSongBinding
import com.example.android_6th.databinding.FragmentSongfileBinding

class SavesongFragment : Fragment() {

    lateinit var binding: FragmentSavesongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavesongBinding.inflate(inflater,container,false)

        return binding.root
    }
}