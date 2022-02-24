package com.example.latihanaplikasi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithObjectActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON  : String = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_object)

        val tvObject : TextView = findViewById(R.id.tv_object_received)

        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON)

        if (person != null) {
            val theText: String = """
            Name : ${person.name.toString()}
            Age : ${person.age.toString()}
            email : ${person.email.toString()}
            City : ${person.city.toString()}
        """.trimIndent()
            tvObject.text =theText
        }

    }
}