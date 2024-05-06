package com.example.android_6th

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// SignUpActivity와 auth 서비스를 연결시켜주는 인터페이스
interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure()
}