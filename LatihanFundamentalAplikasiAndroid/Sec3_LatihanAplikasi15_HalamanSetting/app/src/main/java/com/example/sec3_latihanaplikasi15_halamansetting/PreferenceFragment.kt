package com.example.sec3_latihanaplikasi15_halamansetting

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class PreferenceFragment :PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var NAME : String
    private lateinit var EMAIL : String
    private lateinit var AGE : String
    private lateinit var PHONE : String
    private lateinit var LOVE : String

    private lateinit var namePreference : EditTextPreference
    private lateinit var emailPreference : EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference : EditTextPreference
    private lateinit var isLoveMuPreference : CheckBoxPreference

    companion object {
        private val DEFAULT_VALUE = "Tidak Ada"
    }
    private fun init() {
        NAME = resources.getString(R.string.key_name)
        EMAIL = resources.getString(R.string.key_email)
        AGE = resources.getString(R.string.key_age)
        PHONE = resources.getString(R.string.key_phone)
        LOVE = resources.getString(R.string.key_love)

        namePreference = findPreference<EditTextPreference>(NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLoveMuPreference = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setSummaries() {
        val sharedPref = preferenceManager.sharedPreferences
        if (sharedPref != null) {
            namePreference.summary = sharedPref.getString(NAME, DEFAULT_VALUE)
            emailPreference.summary = sharedPref.getString(EMAIL, DEFAULT_VALUE)
            agePreference.summary = sharedPref.getString(AGE, DEFAULT_VALUE)
            phonePreference.summary = sharedPref.getString(PHONE, DEFAULT_VALUE)
            isLoveMuPreference.isChecked = sharedPref.getBoolean(LOVE, false)
        }

    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when(key) {
            NAME -> {namePreference.summary = sharedPreferences.getString(NAME, DEFAULT_VALUE)}
            EMAIL -> {emailPreference.summary = sharedPreferences.getString(EMAIL, DEFAULT_VALUE)}
            AGE -> {agePreference.summary = sharedPreferences.getString(AGE, DEFAULT_VALUE)}
            PHONE -> {phonePreference.summary = sharedPreferences.getString(PHONE, DEFAULT_VALUE)}
            LOVE -> {isLoveMuPreference.isChecked = sharedPreferences.getBoolean(LOVE, false)}
        }
    }
}