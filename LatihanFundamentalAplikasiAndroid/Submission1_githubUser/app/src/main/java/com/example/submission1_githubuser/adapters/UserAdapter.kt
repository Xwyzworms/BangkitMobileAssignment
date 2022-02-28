package com.example.submission1_githubuser.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1_githubuser.DetailActivity
import com.example.submission1_githubuser.data.User
import com.example.submission1_githubuser.databinding.ItemGithubUserBinding

class UserAdapter(private var userArray : ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder> (){

    private lateinit var onItemClickCallback : OnItemClickCallback

    inner class ListViewHolder(var binding : ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemSelected(data : User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ListViewHolder {
        val binding  : ItemGithubUserBinding= ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = userArray[position]
        holder.binding.ivProfilePict.setImageResource(user.avatar)
        holder.binding.tvAmName.text = user.name
        holder.binding.tvAmTotalfollowers.text = user.followers
        holder.binding.tvAmTotalfollowing.text = user.following
        holder.binding.tvAmUsername.text = user.username

        holder.itemView.setOnClickListener {
           onItemClickCallback.onItemSelected(userArray[position])
        }

    }

    override fun getItemCount(): Int {
       return userArray.size
    }

    fun filterList(arrayList : ArrayList<User>){
        userArray = arrayList
        notifyDataSetChanged()
    }


}



