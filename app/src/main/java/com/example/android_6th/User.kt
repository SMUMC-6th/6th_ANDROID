package com.example.android_6th

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserTable")
//이건 구글에서 만든 데이터 관리 라이브러리? 사용하는건디
//뭐 데이타 테이블 만들어서 맵핑해서 관리하는 그런 데이터베이스?
data class User(
    val email : String,
    var password : String,
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
