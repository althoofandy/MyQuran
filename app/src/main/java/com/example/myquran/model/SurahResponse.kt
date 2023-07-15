package com.example.myquran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurahResponse(
    @SerializedName("arti")
    val arti : String?,

    @SerializedName("asma")
    val asma : String?,

    @SerializedName("ayat")
    val ayat : Int?,

    @SerializedName("nama")
    val nama : String?,

    @SerializedName("type")
    val type : String?,

    @SerializedName("urut")
    val urut : String?,

    @SerializedName("audio")
    val audio : String?,

    @SerializedName("nomor")
    val nomor : String?,

    @SerializedName("rukuk")
    val rukuk : String?,

    @SerializedName("keterangan")
    val keterangan : String?

):Parcelable
