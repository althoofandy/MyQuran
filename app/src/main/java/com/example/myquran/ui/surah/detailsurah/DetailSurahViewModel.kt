package com.example.myquran.ui.surah.detailsurah

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myquran.api.Retrofit
import com.example.myquran.model.DetailSurahResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSurahViewModel: ViewModel() {
    val data = MutableLiveData<List<DetailSurahResponse>>()
    fun getListAyat(nomor: String){
        val retrofit = Retrofit.getApiService().getDetailSurah(nomor)
        retrofit.enqueue(object : Callback<List<DetailSurahResponse>> {
            override fun onResponse(call: Call<List<DetailSurahResponse>>, response: Response<List<DetailSurahResponse>>) {
                data.postValue(response.body())
                Log.d("Msg Success",data.toString())
            }

            override fun onFailure(call: Call<List<DetailSurahResponse>>, t: Throwable) {
                Log.d("Msg","Error")
            }
        })
    }
    fun getDataAyat(): LiveData<List<DetailSurahResponse>> {
        return data
    }
}