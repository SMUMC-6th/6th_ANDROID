package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.MainActivity
import com.example.android_6th.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입 화면으로 전환
        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // 로그인 버튼 클릭 시
        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    // 로그인 함수
    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

//        // 입력한 이메일과 pw가 DB에 존재하는지 확인
//        val songDB = SongDatabase.getInstance(this)!!
//        val user = songDB.userDao().getUser(email, password)
//
//        user?.let { // 유저 정보가 존재할 때
//            Log.d("LOGIN_ACT/GET_USER", "userId: ${user.id}, $user")
//            saveJwt(user.id)
//
//            startMainActivity()
//        }
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email, password, "")) //로그인 API 호출

        // 유저 정보가 존재하지 않을 때
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    // 로그인 완료 후 메인화면으로 넘어가는 함수
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // Jwt 저장해주는 함수
    private fun saveJwt(jwt: Int) {
        val spf = getSharedPreferences("auth" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    // Jwt 저장해주는 함수(String)
    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code : Int, result: Result) {
        //사용자의 Jwt 값 저장
        when(code){
            1000 -> {
                saveJwt2(result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure() {
        //실패처리
//        startActivity(Intent(this, LoginActivity::class.java))
    }


}