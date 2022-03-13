package com.example.sec3_latihanaplikasi14_sharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sec3_latihanaplikasi14_sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mUserpreferences : UserPreferences
    private var isPreferenceEmpty = false
    private lateinit var user : User
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.data != null && it.resultCode == FormUserPreferenceActivity.RESULT_CODE) {
            user = it.data?.getParcelableExtra<User>(FormUserPreferenceActivity.EXTRA_RESULT) as User
            populateView(user)
            checkForm(user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserpreferences = UserPreferences(this)
        showExistingPreference()
        binding.btnSave.setOnClickListener {
            val intent = Intent(this, FormUserPreferenceActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_ADD)
                    intent.putExtra("USER", user)
                }
                else -> {
                    intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_EDIT)
                    intent.putExtra("USER", user)
                }
            }

            resultLauncher.launch(intent)
        }
    }

    private fun showExistingPreference() {
        user = mUserpreferences.getUser()
        populateView(user)
        checkForm(user)
    }

    private fun populateView(user: User) {
        binding.tvName.text = if (user.name.toString().isEmpty()) "Tidak Ada" else user.name
        binding.tvAge.text = if (user.age.toString().isEmpty()) "Tidak Ada" else user.age.toString()
        binding.tvIsLoveMu.text = if (user.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = if (user.email.toString().isEmpty()) "Tidak Ada" else user.email
        binding.tvPhone.text = if (user.phoneNumber.toString().isEmpty()) "Tidak Ada" else user.phoneNumber
    }

    private  fun checkForm(user : User ){
        when {
            user.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }

    }
}