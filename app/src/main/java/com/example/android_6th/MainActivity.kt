package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivityMainBinding
import com.example.flo.Song
import com.example.flo.SongDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding // 바인딩 전역변수 선언



    override fun onCreate(savedInstanceState: Bundle?) {// oncreate는 activity 처음 만들어지면 쓰는거
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // binding에 메모리 주는거
        setContentView(binding.root) // binding에 xml접근 허용 범위


        val song = Song(binding.mainMiniplayerSingerTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerCl.setOnClickListener{

            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("singer", song.singer)
            intent.putExtra("singer",song.singer)

            startActivity(Intent(this,SongActivity::class.java))
        }

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()


        Log.d("Song",song.title +song.singer)
    }

    private fun initBottomNavigation(){


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()


        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()


                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    //데이터를 db에 넣어주는 작업
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )

        )


        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbumes()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "sdfdffs",
                "fdssf",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                1,
                "sdfdsffs",
                "fdsddfsf",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "sdffsfs",
                "fdssf",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "sdfffs",
                "fdssf",
                R.drawable.img_album_exp3
            )
        )


        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

}