package com.example.sec3_latihanaplikasi13_close_open_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sec3_latihanaplikasi13_close_open_data.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButton()

    }



    private fun setupButton() {
        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this@MainActivity, "File Cleared", Toast.LENGTH_LONG).show()
    }

    private fun openData() {
        val items = fileList()
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Pilih File yang Di inginkan")
        alertDialog.setItems(items) {
                dialog, itemIndx -> loadData(items[itemIndx].toString())
        }
        alertDialog.create()
        alertDialog.show()
    }

    private fun loadData(title : String) {
        val fileModel = FileHelper().readFromFile(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)

        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_LONG).show()
    }

    private fun saveFile() {
        when {
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "Yok Isi Kontent Dlu", Toast.LENGTH_LONG ).show()
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Yok Isi Title Dlu", Toast.LENGTH_LONG).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val konten = binding.editFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = konten

                FileHelper().writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " File", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(v1: View) {
        when(v1.id) {
            R.id.button_new -> {newFile()}
            R.id.button_open -> {openData()}
            R.id.button_save -> {saveFile()}
        }

    }
}