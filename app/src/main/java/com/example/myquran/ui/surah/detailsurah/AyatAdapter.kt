package com.example.myquran.ui.surah.detailsurah

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquran.R
import com.example.myquran.databinding.ItemAyatBinding
import com.example.myquran.model.DetailSurahResponse

class AyatAdapter(private val context: Context, private val ayatList: List<DetailSurahResponse>
) : RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AyatAdapter.AyatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ayat, parent, false)
        val binding = ItemAyatBinding.bind(view)
        return AyatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AyatAdapter.AyatViewHolder, position: Int) {
        val item = ayatList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = ayatList.size

    inner class AyatViewHolder(private val binding: ItemAyatBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ayat: DetailSurahResponse){
            val spannedText = Html.fromHtml(ayat?.tr, Html.FROM_HTML_MODE_COMPACT)
            binding.tvAyat.text = ayat.ar
            binding.tvArtiAyat.text = ayat.id
            binding.tvLatinAyat.text = spannedText
            binding.tvSurahNumber.text = ayat.nomor

        }

    }
}