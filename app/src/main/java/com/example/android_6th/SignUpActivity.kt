package com.example.android_6th

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivityLoginBinding
import com.example.android_6th.databinding.ActivitySignupBinding
import com.example.flo.SongDatabase

class SignUpActivity:AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signup()
            finish()
        }
    }

    //사용자 입력값 받아오는 함수
    //EditText view를 통해 입력 받음
    private fun getUser() : User{
        val email : String = binding.signUpIdEt.text.toString() + '@' + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()
        //tostring()을 통해 문자열로 변환
        return User(email,pwd)
    }

    //회원가입 함수
    //vaildation 처리
    private fun signup(){
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일 형식이 잘 못 되었습니다.",Toast.LENGTH_SHORT).show()
            return // 리턴 되면 아래 것들 실행 안됨
        }//아이디 입력 안되면 못함

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
            return // 리턴 되면 아래 것들 실행 안됨
        }//비번 맞는지 확인

        //정보 데이타 베이스에 저장하는 작업
        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        //저장 되었는지 로그로 확인
        val user = userDB.userDao().getUsers()
        Log.d("SIGNPACT",user.toString())



    }

}