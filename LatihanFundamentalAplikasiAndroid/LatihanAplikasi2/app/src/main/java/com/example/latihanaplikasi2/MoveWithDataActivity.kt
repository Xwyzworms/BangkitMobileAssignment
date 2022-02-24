package com.example.latihanaplikasi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithDataActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AGE : String = "extra_age"
        const val EXTRA_NAME : String = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_data)

        val tv_data_received : TextView = findViewById(R.id.tv_data_received)

        val name : String = intent.getStringExtra(EXTRA_NAME) ?: "Default Name"
        val age : Int = intent.getIntExtra(EXTRA_AGE,0)


        val text : String = "Name : $name your age : $age"
        tv_data_received.text = text

    }
}