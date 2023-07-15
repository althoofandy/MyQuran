package com.example.myquran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoaResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("doa")
    val doa: String,

    @SerializedName("ayat")
    val ayat: String,

    @SerializedName("latin")
    val latin: String,

    @SerializedName("artinya")
    val artinya: String,

):Parcelable
