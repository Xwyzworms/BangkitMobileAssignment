package com.example.submission2_ezpz.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.ItemRvHomeBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity

class UserAdapter(private val listUserOwners : List<UserEntity>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    private var itemClickCallbackVariable : ItemClickCallback? = null

    fun setOnItemListener(listener : ItemClickCallback) {
        itemClickCallbackVariable = listener
    }
    private fun bindData(user : UserEntity, holder : ViewHolder) {
        val isFavorView = holder.binding.ivFavorite
        holder.binding.tvHName.text = user.username
        if(user.isFavorite) holder.binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(isFavorView.context, R.drawable.ic_baseline_star_24))
        else holder.binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(isFavorView.context, R.drawable.ic_baseline_star_border_24))
        isFavorView.setOnClickListener {
            Log.d("HELLO",user.isFavorite.toString())
            itemClickCallbackVariable?.onFavoriteClick(user)
        }
        Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.ivHPicture)
    }
    inner class ViewHolder( val binding : ItemRvHomeBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : UserEntity = listUserOwners[position]
        bindData(user, holder)
        holder.itemView.setOnClickListener {
            itemClickCallbackVariable?.onDetailClick(user)
        }
        holder.binding.ivHRepo.setOnClickListener {
            itemClickCallbackVariable?.onGithubIconClick(uri = user.githubUrl.toString())
        }


    }

    override fun getItemCount(): Int {
        return listUserOwners.size
    }

    interface ItemClickCallback {
        fun onDetailClick(user : UserEntity)
        fun onFavoriteClick(user : UserEntity)
        fun onGithubIconClick(uri : String)
    }
}