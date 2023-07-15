package com.example.myquran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityModel(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<DataLokasi>
) : Parcelable

@Parcelize
data class DataLokasi(
    @SerializedName("id")
    val id: String,

    @SerializedName("lokasi")
    val lokasi: String
): Parcelable
