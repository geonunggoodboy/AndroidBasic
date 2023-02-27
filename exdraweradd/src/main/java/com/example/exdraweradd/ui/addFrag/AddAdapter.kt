package com.example.exdraweradd.ui.addFrag

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exdraweradd.databinding.ItemRecyclerBinding

class AddAdapter(private val context: Context): RecyclerView.Adapter<AddAdapter.AddViewHolder>(){

    var datas = mutableListOf<CityData>()
    var cityNames = mutableListOf<String>()

    val checkedItems = mutableListOf<Int>()


    inner class AddViewHolder(private val _binding: ItemRecyclerBinding): RecyclerView.ViewHolder(_binding.root) {
        private val checkBox = _binding.checkSelected
        fun bind(item: CityData){
            cityNames.add(item.name)
            _binding.tvCity.text = item.name
            _binding.tvTime.text = item.date
            _binding.tvTemp.text = item.temp.toString()
            Glide.with(itemView).load(item.icon).into(_binding.ivIconWeather)

            checkBox.isChecked = checkedItems.contains(adapterPosition)
            checkBox.setOnCheckedChangeListener{_, isChecked ->
                if(isChecked){
                    checkedItems.add(adapterPosition)
                    Log.d("AddAdapter", "check ${adapterPosition}")
                } else {
                    checkedItems.remove(adapterPosition)
                    Log.d("AddAdapter", "remove ${adapterPosition}")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(context), parent, false)
        return AddViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: AddViewHolder, position: Int) {
        val city = datas[position]
        holder.bind(city)
    }
}