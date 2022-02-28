package com.example.submission1_githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1_githubuser.adapters.UserAdapter
import com.example.submission1_githubuser.data.User
import com.example.submission1_githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val listUsers : ArrayList<User>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val datausername = resources.getStringArray(R.array.username)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataRepository = resources.getStringArray(R.array.repository)
            val lists = ArrayList<User>()
            for (i in dataName.indices) {
                val user : User = User(
                    datausername[i],dataName[i],dataAvatar.getResourceId(i,-1),
                    dataFollowers[i],dataFollowing[i],dataCompany[i],dataRepository[i],dataLocation[i]
                )
                lists.add(user)
            }
            return lists
        }

    private fun showUserData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val listUsers : ArrayList<User> = listUsers
        val listUserAdapter = UserAdapter(listUsers)
        binding.recyclerView.adapter = listUserAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.setHasFixedSize(true)
        showUserData()
    }
}