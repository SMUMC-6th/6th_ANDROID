package com.example.android_6th

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.android_6th.databinding.ActivityMainBinding
import com.example.android_6th.databinding.FragmentAlbumBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val song = Song()

    // 미니플레이에 적힌 노래 제목과 SongActivity로 전환됐을 때의 노래 제목 일치 여부 확인하는 Toast 메시지
    companion object{ const val STRING_INTENT_KEY = "my_string_key"}
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val returnString = result.data?.getStringExtra(STRING_INTENT_KEY)
            Toast.makeText(this, returnString, Toast.LENGTH_SHORT).show()
        }
    }

    // foreground service 호출
    fun serviceStart(view: View){
        val intent = Intent(this, Foreground::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
    fun serviceStop(view: View){
        val intent = Intent(this, Foreground::class.java)
        stopService(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummyAlbums()

        val song = Song(binding.mainMainplayerTitleTv.text.toString(), binding.mainMainplayerSingerTv.text.toString(), 0, 60, false)

        // 아무런 album의 player 버튼 클릭 안 했을 시 -> 아이유 & 라일락
        if (song.title == "" && song.singer == "") {
            song.title = binding.mainMainplayerTitleTv.text.toString()
            song.singer = binding.mainMainplayerSingerTv.text.toString()
        }
        else{
            setMiniPlayer(song)
        }

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title,)
            intent.putExtra("singer", song.singer,)
            intent.putExtra("second", song.second,)
            intent.putExtra("playTime", song.playTime,)
            intent.putExtra("isPlaying", song.isPlaying,)
            startActivity(intent)
            //getResultText.launch(intent)
        }
        initBottomNavigation()

        Log.d("Song", song.title + song.singer)
        Log.d("MAIN/JWT_TO_SERVER", getJwt().toString())
    }

    private fun getJwt(): String? { //사용자 확인용 jwt 로그
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                        )
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                        )
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                        )
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                        )
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMainplayerTitleTv.text = song.title
        binding.mainMainplayerSingerTv.text = song.singer
    }

    // 앨범 더미데이터 생성 함수
    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )
        songDB.albumDao().insert(
            Album(
                1,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "밤양갱", "비비 (BIBI)", R.drawable.img_album_exp3
            )
        )
        songDB.albumDao().insert(
            Album(
                3,
                "EASY", "LE SSERAFIM", R.drawable.img_album_exp4
            )
        )
        songDB.albumDao().insert(
            Album(
                4,
                "I AM", "IVE (아이브)", R.drawable.img_album_exp5
            )
        )
        songDB.albumDao().insert(
            Album(
                5,
                "Talk Saxy", "RIIZE", R.drawable.img_album_exp6
            )
        )
        songDB.albumDao().insert(
            Album(
                6,
                "Drama", "aespa", R.drawable.img_album_exp7
            )
        )
        songDB.albumDao().insert(
            Album(
                7,
                "To. X", "TAEYEON", R.drawable.img_album_exp8
            )
        )

    }
}