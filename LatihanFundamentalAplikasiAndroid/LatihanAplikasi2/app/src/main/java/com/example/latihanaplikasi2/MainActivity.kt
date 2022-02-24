package com.example.latihanaplikasi2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvResult : TextView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == MoveForResultActivity.RESULT_CODE) {
            val selectedValue : Int = it.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0) ?: 0
            tvResult.text= selectedValue.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tv_result)
        val btnDialPhone : Button = findViewById(R.id.btn_dial_number)
        val btnMoveActivity : Button = findViewById(R.id.btn_move_activity)
        val btnMoveWithData : Button = findViewById(R.id.btn_move_with_data)
        val btnMoveWithObject : Button = findViewById(R.id.btn_move_activity_object)
        val btnMoveForResult : Button = findViewById(R.id.btn_move_for_result)


        btnMoveActivity.setOnClickListener(this)
        btnMoveWithData.setOnClickListener(this)
        btnMoveWithObject.setOnClickListener(this)
        btnDialPhone.setOnClickListener(this)
        btnMoveForResult.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_move_activity -> {
                val moveIntent = Intent(this@MainActivity, MoveAcitvity::class.java)
                startActivity(moveIntent)
            }
            R.id.btn_move_with_data -> {
                val moveIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Prim Ganteng")
                moveIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 99)
                startActivity(moveIntent)
            }
            R.id.btn_move_activity_object -> {
                val person : Person = Person(
                    "Pram Ganteng",
                    5,
                    "Prim@Email.com",
                    "Bandung"
                )
                val moveIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person )
                startActivity(moveIntent)
            }
            R.id.btn_dial_number -> {
                val phoneNumber : String = "082219542419"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }
            R.id.btn_move_for_result -> {
                val moveForResultIntent : Intent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }

        }
    }
}