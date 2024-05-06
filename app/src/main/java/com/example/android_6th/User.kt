package com.example.android_6th

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    @SerializedName(value = "culture") var email: String,
    @SerializedName(value = "born") var password: String,
    @SerializedName(value = "name") var name: String,
//    @SerializedName(value = "aliases") val aliases: List<String>,
//    @SerializedName(value = "allegiances") val allegiances: List<String>,
//    @SerializedName(value = "books") val books: List<String>,
//    @SerializedName(value = "died") val died: String,
//    @SerializedName(value = "father") val father: String,
//    @SerializedName(value = "gender") val gender: String,
//    @SerializedName(value = "mother") val mother: String,
//    @SerializedName(value = "playedBy") val playedBy: List<String>,
//    @SerializedName(value = "povBooks") val povBooks: List<String>,
//    @SerializedName(value = "spouse") val spouse: String,
//    @SerializedName(value = "titles") val titles: List<String>,
//    @SerializedName(value = "tvSeries") val tvSeries: List<String>,
//    @SerializedName(value = "url") val url: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}