package com.example.android_6th

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user : User){ // 회원가입 API를 작성하고 관리하는 함수
        //서비스 Retrofit 객체 생성
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(p0: Call<AuthResponse>, p1: Response<AuthResponse>) {
                Log.d("SIGNUP/SSUCCESS", p1.toString())
                val resp: AuthResponse = p1.body()!!
                when(resp.hashCode()){
                    1000-> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(p0: Call<AuthResponse>, p1: Throwable) {
                Log.d("SIGNUP/FAILURE", p1.message.toString())
            }
        })
        Log.d("SIGNUP/SSUCCESS", "HELLO") //확인용
    }

    fun login(user : User){ // 로그인 API를 작성하고 관리하는 함수
        //서비스 Retrofit 객체 생성
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(p0: Call<AuthResponse>, p1: Response<AuthResponse>) {
                Log.d("LOGIN/SSUCCESS", p1.toString())
                val resp: AuthResponse = p1.body()!!

                when(val code = resp.hashCode()){
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(p0: Call<AuthResponse>, p1: Throwable) {
                Log.d("LOGIN/FAILURE", p1.message.toString())
            }
        })
        Log.d("LOGIN/SSUCCESS", "HELLO") //확인용
    }
}