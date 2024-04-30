package com.example.android_6th

import androidx.room.*

@Dao
interface UserDao {
    @Insert // 사용자 입력
    fun insert(user: User)

    @Query("SELECT * FROM UserTable") // 전체 유저의 정보
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")  // 특정 유저의 정보
    fun getUser(email: String, password: String): User?
}
