package com.example.android_6th

import com.google.gson.annotations.SerializedName

data class AuthResponse( //회원가입
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result") val result: Result?
)

data class Result( //로그인 결과
    @SerializedName(value = "userIdx") var userIdx: Int,
    @SerializedName(value = "jwt") var jwt : String,

)
