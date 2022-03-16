package com.example.submission2_ezpz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.ItemRvHomeBinding

class UserAdapter(private val listUserOwners : List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    private var itemClickCallbackVariable : ItemClickCallback? = null

    fun setOnItemListener(listener : ItemClickCallback) {
        itemClickCallbackVariable = listener
    }
    private fun bindData(user : User, holder : ViewHolder) {
        holder.binding.tvHName.text = user.username.toString()
        Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.ivHPicture)
    }
    inner class ViewHolder( val binding : ItemRvHomeBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User = listUserOwners[position]
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
        fun onDetailClick(user : User)

        fun onGithubIconClick(uri : String)
    }
}