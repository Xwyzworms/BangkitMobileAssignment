package com.example.sec3_latihanaplikasi18_room.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sec3_latihanaplikasi18_room.NoteAdapter
import com.example.sec3_latihanaplikasi18_room.R
import com.example.sec3_latihanaplikasi18_room.databinding.ActivityMainBinding
import com.example.sec3_latihanaplikasi18_room.helper.ViewModelfactory
import com.example.sec3_latihanaplikasi18_room.ui.insert.NoteAddUpdateActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.fetchNotes()
            mainViewModel.getAllNotes().observe(this@MainActivity) {
                if (it != null) {
                        adapter.setListNotes(it)
                }
        }
        adapter = NoteAdapter()
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity : AppCompatActivity): MainViewModel {
        val factory = ViewModelfactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}