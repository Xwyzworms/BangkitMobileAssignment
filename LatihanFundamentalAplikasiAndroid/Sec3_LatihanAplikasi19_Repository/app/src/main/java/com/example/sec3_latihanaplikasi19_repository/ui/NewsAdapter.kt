package com.example.sec3_latihanaplikasi19_repository.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sec3_latihanaplikasi19_repository.R
import com.example.sec3_latihanaplikasi19_repository.data.local.entity.NewsEntity
import com.example.sec3_latihanaplikasi19_repository.databinding.ItemNewsBinding

class NewsAdapter(private val onBookmarkClick : (NewsEntity) -> Unit) : ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemNewsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news : NewsEntity = getItem(position)
        holder.bind(news)
        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_bookmark_24))
        }
        else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_bookmark_border_24))
        }

        ivBookmark.setOnClickListener{
            onBookmarkClick(news)
        }
    }


    inner class MyViewHolder( val binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(news : NewsEntity) {
            binding.tvItemPublishedDate.text = news.publishedAt
            binding.tvItemTitle.text = news.title
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_downloading_24).error(R.drawable.ic_baseline_error_24))
                .into(binding.imgPoster)

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }

        }

    }

    companion object {
        val DIFF_CALLBACK : DiffUtil.ItemCallback<NewsEntity> = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}