package com.example.submission1_githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission1_githubuser.data.User
import com.example.submission1_githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private var user: User ?= null

    companion object {
        const val EXTRA_USER : String = "User"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getParcelableExtra(EXTRA_USER)
        if (user != null) {
            val finalUser : User = user!!
            binding.ivProfile.setImageResource(finalUser.avatar)
            binding.tvAmName.text = finalUser.name
            binding.tvAmUsername.text = finalUser.username
            binding.tvCompany.text = finalUser.company
            binding.tvLocation.text = finalUser.location
            binding.tvRepository.text = finalUser.repository
            binding.tvTotalFollowers.text = finalUser.followers + " Followers"
            binding.tvTotalFollowing.text = finalUser.following + " Following"

        }


    }
}