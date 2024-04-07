package com.example.android_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android_6th.databinding.ActivitySongBinding
import com.example.android_6th.databinding.FragmentAlbumBinding
import com.google.gson.Gson
import kotlinx.coroutines.awaitAll

class AlbumFragment : Fragment() {

    private var gson: Gson = Gson()
    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
        setInit(album)

        binding.albumBackIv.setOnClickListener {

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()

        }

        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setInit(album: Album?) {
        binding.albumAlbumIv.setImageResource(album?.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()


    }
}