package com.example.exflodingappbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exflodingappbar.databinding.ItemBinding


class Adapter: RecyclerView.Adapter<Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind("${position} 번째 아이템~")
    }

    override fun getItemCount(): Int {
        return 50
    }

    inner class MyViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(string: String){
            binding.itemTv.text = string
        }
    }


}