package com.example.myquran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailSurahResponse(
    @SerializedName("ar")
    val ar:String?,

    @SerializedName("id")
    val id:String?,

    @SerializedName("tr")
    val tr:String?,

    @SerializedName("nomor")
    val nomor:String?

):Parcelable
