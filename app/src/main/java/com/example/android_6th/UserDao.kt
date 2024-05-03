package com.example.flo

import androidx.room.*
import com.example.android_6th.User


/*
@Dao
interface UserDao {
    @Insert // 삽입하는 구문
    fun insert(user: User)

    @Query("SELECT * FROM UserTable") // 유저 테이블에 저장된 모든 정보 가져오는
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): User? // ? <- 이건 null처리 값이 없을 수 있으니까.
    //이메일, 비번 받는 구문
}*/

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): User?
}