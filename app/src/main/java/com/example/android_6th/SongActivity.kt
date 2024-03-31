package com.example.android_6th

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivitySongBinding

//AppCompatActivity클래스를 상속 받은 거임 ':'기호 사용함
class SongActivity:AppCompatActivity() {
    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //actividy song의 모든거 다씀
        //괄호 안에 있는 작업을 해라아
        binding.homePannelBtnDownIv.setOnClickListener{

            finish()
        }
        binding.homePannelBtnMusicplayIv.setOnClickListener {
            setplayerStatus(false)
        }
        binding.homePannelBtnMusicstopIv.setOnClickListener{
            setplayerStatus(true)
        }

        if(intent.hasExtra("title") &&intent.hasExtra("singer")){

            binding.homePannelMusictitleEQIv.text=intent.getStringExtra("title")
            binding.homePannelMusicsingerEQIv.text=intent.getStringExtra("singer")
        }
    }

    fun setplayerStatus(isplaying : Boolean){

        if(isplaying){
            binding.homePannelBtnMusicplayIv.visibility = View.VISIBLE
            binding.homePannelBtnMusicstopIv.visibility = View.GONE

        }

        else{
            binding.homePannelBtnMusicplayIv.visibility = View.GONE
            binding.homePannelBtnMusicstopIv.visibility = View.VISIBLE

        }

    }

}