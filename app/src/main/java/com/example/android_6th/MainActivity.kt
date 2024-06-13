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
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

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
        //카카오SDK 초기화
        KakaoSdk.init(this, "959e9225d75104de248c1c98f5a12911") //NATIVE_APP_KEY
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()

        // 아무런 album의 player 버튼 클릭 안 했을 시 -> 아이유 & 라일락
        if (song.title == "" && song.singer == "") {
            song.title = binding.mainMainplayerTitleTv.text.toString()
            song.singer = binding.mainMainplayerSingerTv.text.toString()
        }
        else{
            setMiniPlayer(song)
        }

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }

        initBottomNavigation()

        Log.d("Song", song.title + song.singer)
        Log.d("MAIN/JWT_TO_SERVER", getJwt().toString())

        //kakao 로그인 사용자 정보 요청
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
        }
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

    override fun onStart(){
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null){ //기본값
//            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
//        } else{ //저장된 값
//            gson.fromJson(songJson, song::class.java)
//        }

        //노래 ID 받아오기
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId) //해당 song ID 가져오기
        }

        Log.d("song ID", song.id.toString())

        setMiniPlayer(song)
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMainplayerTitleTv.text = song.title
        binding.mainMainplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }

    // 노래 더미데이터 생성 함수
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

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
                "Butter",
                "방탄소년단 (BTS)",
                0,
                200,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "밤양갱",
                "비비 (BiBi)",
                0,
                190,
                false,
                "music_jelly",
                R.drawable.img_album_exp3,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "EASY",
                "LE SSERAFIM",
                0,
                210,
                false,
                "music_easy",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "I AM",
                "IVE (아이브)",
                0,
                230,
                false,
                "music_iam",
                R.drawable.img_album_exp5,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Talk Saxy",
                "RIIZE",
                0,
                240,
                false,
                "music_talksaxy",
                R.drawable.img_album_exp6,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Drama",
                "aespa",
                0,
                240,
                false,
                "music_drama",
                R.drawable.img_album_exp7,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "To. X",
                "TAEYEON",
                0,
                240,
                false,
                "music_tox",
                R.drawable.img_album_exp8,
                false,
            )
        )
        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
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