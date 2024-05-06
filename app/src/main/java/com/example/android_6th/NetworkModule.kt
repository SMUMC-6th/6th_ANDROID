package com.example.android_6th

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://edu-api-test.softsquared.com"

fun getRetrofit(): Retrofit{ // Retrofit 객체 생성
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}