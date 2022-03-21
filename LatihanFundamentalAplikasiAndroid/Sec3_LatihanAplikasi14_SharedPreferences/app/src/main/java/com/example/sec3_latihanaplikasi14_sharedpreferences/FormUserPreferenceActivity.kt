package com.example.sec3_latihanaplikasi14_sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.sec3_latihanaplikasi14_sharedpreferences.databinding.ActivityFormUserPreferenceBinding

class FormUserPreferenceActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var  binding : ActivityFormUserPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getParcelableExtra<User>("USER") as User
        var actionBarTitle = ""
        var btnTitle = ""
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Tambah Baru"
                btnTitle = "Simpan"
            }
            TYPE_EDIT -> {
                actionBarTitle = "Ubah"
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.text = btnTitle

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()
            val isLoveMU = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

            when {
                name.isEmpty() -> binding.edtName.error = FIELD_REQUIRED
                isValidEmail(email) -> binding.edtEmail.error = FIELD_IS_NOT_VALID
                !age.isDigitsOnly() -> binding.edtAge.error = FIELD_DIGIT_ONLY
                phoneNo.isEmpty() -> binding.edtPhone.error = FIELD_REQUIRED
                !TextUtils.isDigitsOnly(phoneNo) -> binding.edtPhone.error = FIELD_DIGIT_ONLY
                else -> {
                    saveUser(name, email, age, phoneNo, isLoveMU)
                    val intent = Intent()
                    intent.putExtra(EXTRA_RESULT, user)
                    setResult(RESULT_CODE, intent)
                    finish()
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPreferenceInForm() {
        binding.edtName.setText(user.name)
        binding.edtEmail.setText(user.email)
        binding.edtAge.setText(user.age.toString())
        binding.edtPhone.setText(user.phoneNumber)
        if (user.isLove) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }
    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun saveUser(name : String, email : String, age : String, phoneNo : String, isLoveMu : Boolean) {

        val preferences = UserPreferences(this)
        user.name = name
        user.email = email
        user.age = age.toInt()
        user.phoneNumber = phoneNo
        user.isLove = isLoveMu

        preferences.setUser(user)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field Tidak Boleh Kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya Boleh Nurmerik"
        private const val FIELD_IS_NOT_VALID = "Email Tidak Valid"

    }

}