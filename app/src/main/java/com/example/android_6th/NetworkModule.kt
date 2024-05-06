package com.example.android_6th

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit 객체 만들기

const val BASE_URL = "https://edu-api-test.softsquared.com"

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}

