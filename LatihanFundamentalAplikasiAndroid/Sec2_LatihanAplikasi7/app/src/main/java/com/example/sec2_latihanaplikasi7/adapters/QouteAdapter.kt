package com.example.sec2_latihanaplikasi7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sec2_latihanaplikasi7.data_class.Random
import com.example.sec2_latihanaplikasi7.data_class.RandomSecond
import com.example.sec2_latihanaplikasi7.databinding.ItemQouteBinding

class QouteAdapter(private val data : ArrayList<RandomSecond>) : RecyclerView.Adapter<QouteAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemQouteBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QouteAdapter.ViewHolder {
        val binding = ItemQouteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QouteAdapter.ViewHolder, position: Int) {
        holder.binding.tvItem.text = data[position].en
    }

    override fun getItemCount(): Int {
       return data.size
    }

}