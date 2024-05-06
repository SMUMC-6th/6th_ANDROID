package com.example.android_6th

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.sqlite.db.SupportSQLiteOpenHelper
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

        // 가입완료 버튼 클릭 시 회원가입 진행
        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            //finish()
        }
    }

    private fun getUser(): User { // 사용자가 회원가입 시 입력한 값을 가져오는 함수
        val email: String = //EditText란? 사용자가 입력할 수 있는 TextView
            binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()

        val name: String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

//    private fun signUp() { // 회원가입을 진행하는 함수 (8주차 강의)
//
//        // 이메일을 작성하지 않은 경우
//        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return //return을 하는 이유: 현재 if문이 진행되면, 아래의 코드는 진행되지 않도록 하기 위해
//        }
//
//        // 비밀번호와 비밀번호확인이 동일하지 않은 경우
//        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // 사용자가 입력한 정보를 DB에 저장
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        // DB에 저장이 되었는지 Log를 통해서 확인
//        val user = userDB.userDao().getUsers()
//        Log.d("SIGNUPACT", user.toString())
//    }


    // 회원가입 API 연동 (9주차 강의)
    private fun signUp() {
        // 이메일을 작성하지 않은 경우
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return //return을 하는 이유: 현재 if문이 진행되면, 아래의 코드는 진행되지 않도록 하기 위해
        }

        // 이름을 작성하지 않은 경우
        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // 비밀번호와 비밀번호확인이 동일하지 않은 경우
        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

//        // 서비스 객체 생성 (9주차: 실습 회원가입)
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.signUp(getUser()).enqueue(object: Callback<AuthResponse> { //signUp 괄호 안에 사용자의 정보를 넣어주면 API를 호출하게 된다 // enquere: 응답 처리
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) { // 응답이 왔을 때
//                Log.d("SIGNUP/SUCCESS", response.toString())
//                val resp: AuthResponse = response.body()!!
//                when(resp.code){
//                    1000->finish()
//                    2016, 2018 -> {
//                        binding.signUpEmailErrorTv.visibility = View.VISIBLE
//                        binding.signUpEmailErrorTv.text = resp.message
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) { // 응답 실패 시
//                Log.d("SIGNUP/FAILURE", t.message.toString())
//            }
//
//        })
//        // 현재 작업이 비동기 작업이므로 함수가 잘 실행 되는지 log를 통해 확인
//        Log.d("SIGNUP", "HELLO")
        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }


    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        TODO("Not yet implemented")
    }
}