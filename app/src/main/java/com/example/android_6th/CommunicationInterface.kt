package com.example.android_6th

interface CommunicationInterface {

    // MainActivity와 HomeFragment의 인터페이스 연결
    fun sendData(album: Album)
}