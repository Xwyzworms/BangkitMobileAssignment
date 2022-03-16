package com.example.submission2_ezpz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.ItemRvDetailBinding

class DetailUserAdapter(private  val list_followers: List<User>) : RecyclerView.Adapter<DetailUserAdapter.ViewHolder>() {


    private var onItemDuClickedListener : OnItemDuClicked? =null

    fun setOnItemDuClickedListener(listener : OnItemDuClicked) {
        onItemDuClickedListener = listener
    }

    interface OnItemDuClicked {
        fun onItemClicked(user : User)
        fun onGithubClicked(github_url : String)
    }

    private fun attachDataToListener(user : User, holder : ViewHolder) {
        holder.itemView.setOnClickListener {
            onItemDuClickedListener?.onItemClicked(user)
        }
        holder.binding.ivIduRepo.setOnClickListener {
            onItemDuClickedListener?.onGithubClicked(user.githubUrl)
        }
    }

    private fun bindData(user : User, holder: ViewHolder) {
        holder.binding.tvIduUsername.text = user.username
        Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.ivIduPicture)
        attachDataToListener(user, holder)
    }
    inner class ViewHolder(val binding : ItemRvDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingTemp = ItemRvDetailBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(bindingTemp)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(list_followers[position],holder)

    }

    override fun getItemCount(): Int {
        return list_followers.size
    }
}