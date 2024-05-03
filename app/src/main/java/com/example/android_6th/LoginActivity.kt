package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivityLoginBinding
import com.example.flo.SongDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        //로그인 실행 함수
        binding.loginSignInBtn.setOnClickListener {
            login()
        }

    }

    //로그인 기능 함수
    private  fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //이메일 입력 했냐?
        }
        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //비번 입력 했냐?
        }
        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()

        //아이디 값 비번 값 DB에서 받아야 함.
        //DB 연결
        val songDB = SongDatabase.getInstance(this)!!
        val user = songDB.userDao().getUser(email,pwd)
        //getuser는 Dao에서 작성한 함수인데 디비에서 원하는 값 받아오는거임

        //user 가 NULL인지 확인
        user?.let {
            Log.d("LOGIN_ACT/GET_USER", "userId: ${user.id}, $user")
            saveJwt(user.id)

            startMainActivity()
        }
        //아이디 값은 토큰으로 jwt로 저장된다?
        Toast.makeText(this,"회원 정보가 존재하지 않습니다.",Toast.LENGTH_SHORT).show()


    }
    //로그인 되면 시작하는 함수
    private  fun startMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    //아래 함수의 인자 값이 user의 id값이다.
    //getSharedPreferencesd <-이건 데이터를 파일로 저장한다.
    private fun saveJwt(jwt:Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit() // 에디터?

        //jwt를 jwt로 저장
        editor.putInt("jwt",jwt)
        editor.apply()
    }

}


