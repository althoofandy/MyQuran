package com.example.myquran.ui.surah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquran.databinding.FragmentSurahBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class SurahFragment : Fragment() {
    private var _binding: FragmentSurahBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private lateinit var viewModel: SurahViewModel
    private lateinit var adapter: SurahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSurahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SurahViewModel::class.java]

        binding.rvListSurah.layoutManager = LinearLayoutManager(context)
        binding.rvListSurah.setHasFixedSize(true)

        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        val formattedDate = today.format(formatter)
        binding.tvDayToday.text = "$dayOfWeek, $formattedDate"


        viewModel.getListSurah()
        viewModel.getDataSurah().observe(viewLifecycleOwner){
            adapter = SurahAdapter(requireContext(),it)
            binding.rvListSurah.adapter = adapter
        }

    }

}