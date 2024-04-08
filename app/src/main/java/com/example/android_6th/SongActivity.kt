package com.example.android_6th

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //activity_song.xml 파일의 view들을 쓸 것이다.
        binding.songDownIb.setOnClickListener{
            finish() //activity를 꺼주는 것
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }


        /*binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
            startStopService()
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
            startStopService()
        }*/

    }
    fun setPlayerStatus (isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    /*private fun startStopService() {
        if (isServiceRunning(Foreground::class.java)) {
            Toast.makeText(this, "Foreground Service Stopped", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, Foreground::class.java))
        }
        else {
            Toast.makeText(this, "Foreground Service Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this, Foreground::class.java))
        }
    }*/

    /*private fun isServiceRunning(inputClass : Class<Foreground>) : Boolean {
        val manager : ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (inputClass.name.equals(service.service.className)) {
                return true
            }

        }
        return false
    }*/



}