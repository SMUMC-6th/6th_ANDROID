package com.example.android_6th

import com.example.flo.Song

data class Locker(

    var title:String? = "",
    var singer:String = "",
    var coverImg: Int? = null,
    var songs:ArrayList<Song>? = null

)
