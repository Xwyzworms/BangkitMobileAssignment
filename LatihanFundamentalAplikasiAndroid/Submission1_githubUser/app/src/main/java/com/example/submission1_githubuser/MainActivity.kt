package com.example.submission1_githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1_githubuser.adapters.UserAdapter
import com.example.submission1_githubuser.data.User
import com.example.submission1_githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var listUserAdapter : UserAdapter
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
        listUserAdapter = UserAdapter(listUsers)
        binding.recyclerView.adapter = listUserAdapter
        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemSelected(data: User) {
                showUserDetails(data)
            }

        })

    }

    private fun showUserDetails(user : User) {
        val intent = Intent(this@MainActivity,DetailActivity::class.java )
        intent.putExtra(DetailActivity.EXTRA_USER,user)
        startActivity(intent)

    }


    private fun filter(text : String, adapter : UserAdapter,arrayList : ArrayList<User> ) {
        val filteredList = ArrayList<User>()
        for (user in arrayList) {
            if (user.username.lowercase().contains(text.lowercase())) {
                filteredList.add(user)
            }
        }
        adapter.filterList(filteredList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.setHasFixedSize(true)
        showUserData()

        binding.searchableEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                    filter(p0.toString(),listUserAdapter,listUsers )
            }

        })



    }
}