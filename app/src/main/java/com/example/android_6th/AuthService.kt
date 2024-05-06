package com.example.android_6th

import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){ //signUpView를 연결시켜주는 함수
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user : User){

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signUp(user).enqueue(object: Callback<AuthResponse> { //signUp 괄호 안에 사용자의 정보를 넣어주면 API를 호출하게 된다 // enquere: 응답 처리
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) { // 응답이 왔을 때
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000-> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) { // 응답 실패 시
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        Log.d("SIGNUP", "HELLO")
    }

    fun login(user : User){

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse> { //signUp 괄호 안에 사용자의 정보를 넣어주면 API를 호출하게 된다 // enquere: 응답 처리
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) { // 응답이 왔을 때
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(val code = resp.code){
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) { // 응답 실패 시
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })
        Log.d("LOGIN", "HELLO")
    }
}