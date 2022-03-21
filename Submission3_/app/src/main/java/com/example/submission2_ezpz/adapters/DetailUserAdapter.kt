package com.example.submission2_ezpz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.databinding.ItemRvDetailBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity

class DetailUserAdapter(private val listFollower : List<UserEntity>) : RecyclerView.Adapter<DetailUserAdapter.ViewHolder>() {


    var disabledFavorite : Boolean = false
        set(disable)  {
            field = disable
        }
    private var onItemDuClickedListener: OnItemDuClicked? = null

    fun setOnItemDuClickedListener(listener: OnItemDuClicked) {
        onItemDuClickedListener = listener
    }



    interface OnItemDuClicked {
        fun onItemClicked(user: UserEntity)
        fun onGithubClicked(github_url: String)
        fun onFavoriteClicked(user: UserEntity)
    }

    private fun attachDataToListener(user: UserEntity, holder: ViewHolder) {
        holder.itemView.setOnClickListener {
            onItemDuClickedListener?.onItemClicked(user)
        }
        holder.binding.ivIduRepo.setOnClickListener {
            onItemDuClickedListener?.onGithubClicked(user.githubUrl)
        }
        holder.binding.ivFavorite.setOnClickListener {
            onItemDuClickedListener?.onFavoriteClicked(user)
        }
    }

    private fun bindData(user: UserEntity, holder: ViewHolder) {

        holder.binding.tvIduUsername.text = user.username

        if(disabledFavorite) {
            holder.binding.ivFavorite.visibility = View.GONE
        }
        else {
            val isFavorView = holder.binding.ivFavorite
            if (user.isFavorite) isFavorView.setImageDrawable(
                ContextCompat.getDrawable(
                    isFavorView.context,
                    R.drawable.ic_baseline_star_24
                )
            )
            else isFavorView.setImageDrawable(
                ContextCompat.getDrawable(
                    isFavorView.context,
                    R.drawable.ic_baseline_star_border_24
                )
            )
        }
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(
                RequestOptions
                    .placeholderOf(R.drawable.ic_baseline_downloading_24)
                    .error(R.drawable.ic_baseline_error_24)
            )
            .into(holder.binding.ivIduPicture)
        attachDataToListener(user, holder)


    }

    inner class ViewHolder(val binding: ItemRvDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingTemp =
            ItemRvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingTemp)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(listFollower[position], holder)

    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

}