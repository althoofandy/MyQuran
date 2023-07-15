package com.example.myquran.ui.pray

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquran.R
import com.example.myquran.databinding.FragmentPrayBinding
import com.example.myquran.model.DataLokasi
import com.example.myquran.ui.pray.pref.PrayPref
import com.vivekkaushik.datepicker.OnDateSelectedListener
import java.util.*

class PrayFragment : Fragment() {
    private var _binding: FragmentPrayBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private lateinit var viewModel: PrayViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var sharedPreferenceManager: PrayPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferenceManager = PrayPref(requireContext())
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PrayViewModel::class.java]

        getDataDoa()
        getDataJadwal()

        binding.apply {
            ivSearch.setOnClickListener {
                searchCity()
            }
            edSearch.setOnKeyListener { _, keyCode, event ->
                if(event.action == KeyEvent.ACTION_DOWN && keyCode== KeyEvent.KEYCODE_ENTER){
                    searchCity()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

    }
    fun searchCity(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResult.setHasFixedSize(true)
        binding.apply {
            val cityName = edSearch.text.toString()
            if(cityName.isEmpty())return
            viewModel.getCityIdbySearch(cityName)
            viewModel.getCityId().observe(viewLifecycleOwner){
                if(it.data!=null){
                    adapter = SearchAdapter(requireContext(),it.data)
                    binding.rvSearchResult.adapter = adapter
                    adapter.setOnClickCallback(object : SearchAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: DataLokasi) {
                          val cityId = data.id
                            sharedPreferenceManager.saveDataId(data.id)
                            viewModel.getJadwalToday(cityId,year.toString(),month.toString(),day.toString())

                        }
                    })

                }else{
                    Log.e("Error nih",it.message)
                }

            }
        }
    }
    fun getDataJadwal(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val cityId = sharedPreferenceManager.getDataId()
        val selectedCityId= if (cityId.isNullOrEmpty()) {
            "1301"
        } else {
            cityId
        }
        binding.apply {
            datePickerTimeline.setInitialDate(year, month, day)
            datePickerTimeline.setDisabledDateColor(R.color.hijautua)
            datePickerTimeline.setActiveDate(calendar)

                datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
                    override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                        datePickerTimeline.setDateTextColor(Color.BLACK);
                        datePickerTimeline.setDayTextColor(Color.BLACK);
                        datePickerTimeline.setMonthTextColor(Color.BLACK);
                        Toast.makeText(requireContext(),"tanggal yang dipilih $day dan tahun $year",Toast.LENGTH_LONG).show()
                        viewModel.getJadwalToday(selectedCityId,year.toString(),month.toString(),day.toString())
                    }
                    override fun onDisabledDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int, isDisabled: Boolean) {
                        viewModel.getJadwalToday(selectedCityId,year.toString(),month.toString(),day.toString())

                    }
                })


            val dates: Array<Date> = arrayOf<Date>(Calendar.getInstance().getTime())
            datePickerTimeline.deactivateDates(dates)
            viewModel.getJadwalToday(selectedCityId,year.toString(),month.toString(),day.toString())

            viewModel.getJadwal().observe(viewLifecycleOwner){
                binding.apply {
                    tvCity.text = "${it.lokasi}, ${it.daerah}"
                }
            }
            viewModel.getJadwalSholat().observe(viewLifecycleOwner){
                binding.apply {
                    tvDate.text = it.date
                    tvShubuh.text = it.subuh
                    tvDzuhur.text = it.dzuhur
                    tvAshar.text = it.ashar
                    tvMaghrib.text = it.maghrib
                    tvIsya.text = it.isya
                }
            }
        }
    }

    fun getDataDoa(){
        viewModel.getDoa()
        viewModel.getDataDoa().observe(viewLifecycleOwner){doa ->
            binding.apply {
                for(bacaan in doa){
                    tvJudulDoa.text = bacaan.doa
                    tvArabDoa.text = bacaan.ayat
                    tvLatinDoa.text = bacaan.latin
                    tvDoaMean.text = bacaan.artinya
                }

            }
        }
    }



}