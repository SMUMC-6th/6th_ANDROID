package com.example.android_6th

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), SignUpView {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener { //회원가입 버튼 클릭 시
            signUp()
            finish()
        }
    }

    // 사용자가 입력한 회원가입 정보 값 가져오기
    private fun getUser(): User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

//    private fun signUp() {
//        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser()) //DB 테이블에 넣기
//
//        val users = userDB.userDao().getUsers()
//        Log.d("SIGNUPACT", users.toString()) //사용자 전체 정보 확인하기 (DB에 잘 저장 됐는지 확인용)
//    }

    // 회원가입 함수
    private fun signUp(){
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

//        //서비스 Retrofit 객체 생성
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.signUp(getUser()).enqueue(object: Callback<AuthResponse>{
//            override fun onResponse(p0: Call<AuthResponse>, p1: Response<AuthResponse>) {
//                Log.d("SIGNUP/SSUCCESS", p1.toString())
//                val resp: AuthResponse = p1.body()!!
////                when(resp.code){
////                    1000->finish()
////                    2016, 2017 ->{ //이메일 에러처리
////                        binding.signUpEmailErrorTv.visibility = View.VISIBLE
////                        binding.signUpEmailErrorTv.text = resp.message
////                    }
////                }
//            }
//
//            override fun onFailure(p0: Call<AuthResponse>, p1: Throwable) {
//                Log.d("SIGNUP/FAILURE", p1.message.toString())
//            }
//        })
//        Log.d("SIGNUP/SSUCCESS", "HELLO") //확인용

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser()) //회원가입 API 호출
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {

    }
}