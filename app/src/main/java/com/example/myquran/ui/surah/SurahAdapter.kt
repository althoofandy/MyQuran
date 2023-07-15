package com.example.myquran.ui.surah

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquran.R
import com.example.myquran.databinding.ItemSurahBinding
import com.example.myquran.model.SurahResponse
import com.example.myquran.ui.surah.detailsurah.DetailSurahActivity
import com.example.myquran.ui.surah.detailsurah.DetailSurahActivity.Companion.DATA_SURAH

class SurahAdapter(private val context: Context,private val surahList: List<SurahResponse>
) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurahAdapter.SurahViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_surah, parent, false)
        val binding = ItemSurahBinding.bind(view)
        return SurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurahAdapter.SurahViewHolder, position: Int) {
       val item = surahList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = surahList.size
    inner class SurahViewHolder(private val binding: ItemSurahBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(surah:SurahResponse){
            binding.tvSurahName.text = surah.nama
            binding.tvArtiSurah.text = surah.arti
            binding.tvSurahNumber.text = surah.nomor
            binding.tvJumlahAyat.text = "${surah.ayat.toString()} Ayat"

            itemView.setOnClickListener {
                val surah = SurahResponse(surah.arti,surah.asma,surah.ayat,surah.nama,surah.type,surah.urut,surah.audio,surah.nomor,surah.rukuk,surah.keterangan)
                val intent = Intent(context, DetailSurahActivity::class.java).apply{
                    putExtra(DATA_SURAH,surah)
                }
                context.startActivity(intent)




            }

        }

    }
}