package com.example.myquran.ui.pray

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myquran.api.Retrofit
import com.example.myquran.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrayViewModel: ViewModel() {
    val dataDoa = MutableLiveData<List<DoaResponse>>()
    val dataSholat = MutableLiveData<DataSholat>()
    val dataJadwal = MutableLiveData<JadwalSholat>()
    fun getDoa(){
        val retrofit = Retrofit.getApiServiceDoa().getDoa()
        retrofit.enqueue(object : Callback<List<DoaResponse>>{
            override fun onResponse(call: Call<List<DoaResponse>>, response: Response<List<DoaResponse>>) {
                dataDoa.postValue(response.body())
                Log.d("Cek data doa", dataDoa.toString())
            }

            override fun onFailure(call: Call<List<DoaResponse>>, t: Throwable) {
                Log.d("Cek data doa", "error")
            }

        })
    }

    fun getJadwalToday(cityId : String, year : String,month: String, day : String){
        val retrofit = Retrofit.getApiServiceJadwal().getJadwalToday(cityId,year,month,day)
        retrofit.enqueue(object : Callback<PrayModel>{
            override fun onResponse(call: Call<PrayModel>, response: Response<PrayModel>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let { data ->
                        val responseJadwal = data.jadwal
                        dataSholat.postValue(responseData!!)
                        dataJadwal.postValue(responseJadwal)

                        val subuh = responseJadwal.subuh
                        val terbit = responseJadwal.terbit
                        val dzuhur = responseJadwal.dzuhur
                        val ashar = responseJadwal.ashar
                        val maghrib = responseJadwal.maghrib
                        val isya = responseJadwal.isya

                        Log.d("Cek data doa", "Subuh: $subuh, Terbit: $terbit, Dzuhur: $dzuhur, Ashar: $ashar, Maghrib: $maghrib, Isya: $isya")
                    }
                } else{
                    Log.e("Cek data doa", "Error")
                }

            }

            override fun onFailure(call: Call<PrayModel>, t: Throwable) {
                Log.d("Cek data doa", t.message.toString())
            }

        })
    }

    val dataCityId = MutableLiveData<CityModel>()
    fun getCityIdbySearch(keyword:String){
        val retrofit = Retrofit.getApiServiceJadwal().getCityIdbySearch(keyword)
        retrofit.enqueue(object : Callback<CityModel>{
            override fun onResponse(call: Call<CityModel>, response: Response<CityModel>) {
                if (response.isSuccessful) {
                    dataCityId.postValue(response.body())
                } else {
                    Log.e("Error :", response.message().toString())
                }
            }
            override fun onFailure(call: Call<CityModel>, t: Throwable) {
                Log.e("Error :", t.message.toString())
            }

        })
    }

    fun getDataDoa():LiveData<List<DoaResponse>>{
        return dataDoa
    }
    fun getJadwal():LiveData<DataSholat>{
        return dataSholat
    }
    fun getJadwalSholat(): LiveData<JadwalSholat>{
        return dataJadwal
    }

    fun getCityId(): LiveData<CityModel>{
        return dataCityId
    }
}

