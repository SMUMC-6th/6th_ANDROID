package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_6th.MainActivity
import com.example.android_6th.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // 회원가입 화면으로 전환
        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // 로그인 버튼 클릭 시
        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        //kakao hash key 발급
        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")

        // 카카오 이미지 클릭 시 로그인 호출
        binding.loginKakakoLoginIv.setOnClickListener { kakaoLogin() }

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

        //카카오SDK 초기화
        KakaoSdk.init(this, "959e9225d75104de248c1c98f5a12911") //NATIVE_APP_KEY

        //사용자가 로그인되어 있는지 확인하고, 로그인되어 있다면 메인 액티비티로 이동
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error -> //토큰 유효성 검사
                if (error == null) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
        setContentView(binding.root)
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


    //카카오 로그인
    private fun kakaoLogin(){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우의 콜백
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}