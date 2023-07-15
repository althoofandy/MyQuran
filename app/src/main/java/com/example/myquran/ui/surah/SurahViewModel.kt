package com.example.myquran.ui.surah

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myquran.api.Retrofit
import com.example.myquran.model.SurahResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurahViewModel: ViewModel() {
    val data = MutableLiveData<List<SurahResponse>>()
    fun getListSurah(){
    val retrofit = Retrofit.getApiService().getSurah()
    retrofit.enqueue(object : Callback<List<SurahResponse>> {
        override fun onResponse(call: Call<List<SurahResponse>>, response: Response<List<SurahResponse>>) {
            data.postValue(response.body())
            Log.d("Msg Success",data.toString())
        }

        override fun onFailure(call: Call<List<SurahResponse>>, t: Throwable) {
            Log.d("Msg","Error")
        }
    })
}
    fun getDataSurah(): LiveData<List<SurahResponse>>{
        return data
    }
}