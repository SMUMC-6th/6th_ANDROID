package com.example.android_6th

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users") // 회원가입 URL (명세서 참고)
    fun signUp(@Body user:User) : Call<AuthResponse> //Call<> 안에 들어갈 내용은 response로 받을 값을 미리 생성하는 것

    @POST("/users/login")
    fun login(@Body user:User) : Call<AuthResponse>

}