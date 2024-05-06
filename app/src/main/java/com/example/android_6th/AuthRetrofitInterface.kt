package com.example.android_6th

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users") //회원가입
    fun signUp(@Body user:User): Call<AuthResponse> //응답값(AuthResponse) 리스트로 변환

    @POST("/users/login") //로그인
    fun login(@Body user:User): Call<AuthResponse> //응답값(AuthResponse) 리스트로 변환
}