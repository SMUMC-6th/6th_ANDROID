package com.example.android_6th

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong() // 현재 노래 데이터 받아오기
        setPlayer(song) // 받아온 데이터로 노래 및 가수 설정

        //미니플레이어 노래 제목 데이터 Main에 전달
        binding.songDownIb.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply{
                putExtra(MainActivity.STRING_INTENT_KEY, intent.getStringExtra("title"))
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener{ setPlayerStatus(true) }
        binding.songPauseIv.setOnClickListener{ setPlayerStatus(false) }

        binding.songRepeatIv.setOnClickListener{ setRepeatStatus(true) }
        binding.songRepeatOnIv.setOnClickListener{ setRepeatStatus(false) }

        binding.songRandomIv.setOnClickListener{ setRandomStatus(true) }
        binding.songRandomOnIv.setOnClickListener{ setRandomStatus(false) }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if (second >= playTime){ //현재 재생 시간이 총 재생 시간을 초과할 때 while 루프를 종료
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }

                }
            }catch (e: InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    fun setRepeatStatus(isRepeat : Boolean){
        if(isRepeat){
            binding.songRepeatOnIv.visibility = View.VISIBLE
            binding.songRepeatIv.visibility = View.GONE
        }
        else{
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatIv.visibility = View.VISIBLE
        }
    }

    fun setRandomStatus(isRandom : Boolean){
        if(isRandom){
            binding.songRandomOnIv.visibility = View.VISIBLE
            binding.songRandomIv.visibility = View.GONE
        }
        else{
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomIv.visibility = View.VISIBLE
        }
    }
}