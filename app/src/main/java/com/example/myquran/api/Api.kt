package com.example.myquran.api

import com.example.myquran.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("data")
    fun getSurah(): Call<List<SurahResponse>>

    @GET("surat/{nomor}")
    fun getDetailSurah(
        @Path("nomor") nomor: String?
    ): Call<List<DetailSurahResponse>>

    @GET("doa/v1/random")
    fun getDoa(): Call<List<DoaResponse>>

    @GET("sholat/jadwal/{cityId}/{year}/{month}/{day}")
    fun getJadwalToday(
        @Path("cityId") cityId: String?,
        @Path("year") year: String?,
        @Path("month") month: String?,
        @Path("day") day: String?
    ): Call<PrayModel>

    @GET("sholat/kota/cari/{keyword}")
    fun getCityIdbySearch(
        @Path("keyword") keyword: String?
    ): Call<CityModel>

}