package com.example.android_6th

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
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

//    private var songs = ArrayList<Song>()
//    private var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) //activity_song.xml 파일의 view들을 쓸 것이다.

        initSong()
        setPlayer(song)


        binding.songDownIb.setOnClickListener{
            finish() //activity를 꺼주는 것
        }

        // 재생 버튼 클릭 이벤트
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

//        // 반복 재생 버튼 클릭 이벤트
//        binding.songRepeatIv.setOnClickListener {
//            setRepeatStatus(false)
//        }
//        binding.songRepeatOnIv.setOnClickListener {
//            setRepeatStatus(true)
//        }
//
//        // 전체 재생 버튼 클릭 이벤트
//        binding.songRandomIv.setOnClickListener {
//            setRandomStatus(false)
//        }
//        binding.songRandomActiveIv.setOnClickListener {
//            setRandomStatus(true)
//        }


        /*//repeat 버튼 상태 조작
        if (songs[nowPos].isRepeated) setRepeatStatus(1)
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(0)
            Toast.makeText(this, "전체 음악을 반복합니다.", Toast.LENGTH_SHORT).show()
        }
        binding.songBtnRepeatOnIv.setOnClickListener {
            setRepeatStatus(1)
            Toast.makeText(this, "현재 음악을 반복합니다.", Toast.LENGTH_SHORT).show()

        }
        binding.songBtnRepeatOn1Iv.setOnClickListener {
            setRepeatStatus(2)
            Toast.makeText(this, "반복을 사용하지 않습니다.", Toast.LENGTH_SHORT).show()
        }*/

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
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)

    }


    // 재생 버튼 클릭 시 이벤트 함수
    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){ // 재생중
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else { // 일시정지
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

//    // 반복 재생 클릭 시 이벤트 함수
//    fun setRepeatStatus(repeat: Boolean){
//        if(repeat){
//            binding.songRepeatIv.visibility = View.GONE
//            binding.songRepeatOnIv.visibility = View.VISIBLE
//        }
//        else {
//            binding.songRepeatIv.visibility = View.VISIBLE
//            binding.songRepeatOnIv.visibility = View.GONE
//        }
//    }
//
//    // 전체 재생 클릭 시 이벤트 함수
//    fun setRandomStatus(random: Boolean){
//        if(random){
//            binding.songRandomIv.visibility = View.VISIBLE
//            binding.songRandomActiveIv.visibility = View.GONE
//        }
//        else {
//            binding.songRandomIv.visibility = View.GONE
//            binding.songRandomActiveIv.visibility = View.VISIBLE
//        }
//    }


    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }


    //스레드
    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if(second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }

                    }

                }

            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }


        }

    }

}