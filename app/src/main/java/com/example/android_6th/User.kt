package com.example.android_6th

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// 회원가입 시 사용자 정보를 저장하는 데이터 클래스(테이블)
// 이메일, 비밀번호, id
@Entity(tableName = "UserTable")
data class User(
    @SerializedName(value = "email") var email : String, //JSON으로 request 값을 보낼 때는 @SerializedName을 작성함
    @SerializedName(value = "password") var password : String,
    @SerializedName(value = "name") var name : String
){
    // 사용자가 추가될 때마다 자동적으로 카운트
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
