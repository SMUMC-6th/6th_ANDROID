package com.example.android_6th

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB : SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong() // 현재 노래 데이터 받아오기
        initClickListener()

    }
    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos]) //현재 노래 데이터로 노래 및 가수 설정
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun initClickListener(){
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

        //다음 곡 넘기기
        binding.songNextIv.setOnClickListener {
            moveSong(1)
        }

        //이전 곡 넘기기
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        // 좋아요 하트
        binding.songLikeIv.setOnClickListener { setLike(songs[nowPos].isLike) }
    }

    private fun moveSong(direct: Int){ //곡 인덱스포지션 계산 함수 (이전곡, 다음곡)
        if(nowPos + direct < 0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this,"Last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        timer.interrupt() //time 쓰레드 멈춤
        startTimer() //쓰레드 재실행

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

//        val music = resources.getIdentifier(song.music, "raw", this.packageName)
//        mediaPlayer = MediaPlayer.create(this, music) //music 플레이어에게 재생할 음악 알리기

        if(song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id) //DB에 저장

        if(!isLike){
            Toast.makeText(this,"저장한 곡에 저장되었습니다.", Toast.LENGTH_SHORT).show()
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            Toast.makeText(this,"저장한 곡에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
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

    override fun onPause(){ //사용자가 포커스를 잃었을 때 음악이 중지
        super.onPause()

        songs[nowPos].second = ((binding.songProgressSb.progress)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //내부 저장소에 데이터를 저장 - 간단한 값 저장에 유용(진행되던 음악 분초 저장)
        val editor = sharedPreferences.edit() //에디터

        editor.putInt("songId", songs[nowPos].id)

        editor.apply() //적용(저장)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어플레이어 해제
    }

    fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start() //플레이어 재생
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause() //플레이어 정지
            }
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