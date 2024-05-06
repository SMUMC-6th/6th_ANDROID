package com.example.android_6th

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert //user에 대한 정보를 넣는다
    fun insert(user:User)

    @Query("SELECT * FROM UserTable") //UserTable에 저장된 모든 정보들을 가져온다
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password") // 1명의 user 정보만 가져온다
    fun getUser(email:String, password:String) : User?
}