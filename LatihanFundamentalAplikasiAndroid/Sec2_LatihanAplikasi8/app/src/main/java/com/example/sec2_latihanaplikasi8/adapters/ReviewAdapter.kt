package com.example.sec2_latihanaplikasi8.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sec2_latihanaplikasi8.databinding.ItemReviewBinding

class ReviewAdapter(private val listReview : List<String>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItem.text = listReview[position]
    }

    override fun getItemCount(): Int {
        return listReview.size
    }
}