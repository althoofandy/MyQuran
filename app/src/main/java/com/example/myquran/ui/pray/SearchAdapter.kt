package com.example.myquran.ui.pray

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myquran.R
import com.example.myquran.databinding.ItemSearchBinding
import com.example.myquran.model.DataLokasi


class SearchAdapter(private val context: Context, private val searchList: List<DataLokasi>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var isExpanded = true
    private val _dataCityId = MutableLiveData<String>()
    val dataCityId: LiveData<String> = _dataCityId
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_search, parent, false)
        val binding = ItemSearchBinding.bind(view)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val item = searchList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = searchList.size
    inner class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(search: DataLokasi){

            binding.tvCityName.text = search.lokasi

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(search)
                isExpanded = !isExpanded
                notifyDataSetChanged()
            }
            if (isExpanded) {
                binding.root.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                binding.root.layoutParams.height = 0
            }

        }

    }
    interface OnItemClickCallback{
        fun onItemClicked(data: DataLokasi)
    }
}