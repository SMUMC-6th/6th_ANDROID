package com.example.android_6th

import com.google.gson.annotations.SerializedName

// response 받을 값을 데이터 클래스로 정의함
data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result") val result: Result?
)
data class Result(
    @SerializedName(value = "userIdx") var userIdx: Int,
    @SerializedName(value = "jwt") var jwt: String
)
