package com.example.android_6th

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbumes():List<Album>

    //삽입
    @Insert
    fun likeAlbum(like: Like)

    //좋아요 눌렀음?
    //userid와 albumid를 비교해서 liketable에 있는지 확인한다.
    @Query("SELECT id FROM LikeTable Where userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId:Int, albumId: Int) : Int?// 없으면 눌 준다.

    //사용자가 좋아요 취소하는 거 받는 놈
    @Query("DELETE FROM LikeTable Where userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId: Int)

    //보관함에서 유저를 분류해서 좋아요 한 앨범 가져온다.
    //as는 그냥 define같은 느낌 LIKETABLE을 LT로 쓴다는 말
    //join?
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getlikedAlbums(userId: Int) : List<Album>


}