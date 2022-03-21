package com.example.sec3_latihanaplikasi18_room.ui.insert

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sec3_latihanaplikasi18_room.R
import com.example.sec3_latihanaplikasi18_room.database.Note
import com.example.sec3_latihanaplikasi18_room.databinding.ActivityNoteAddUpdateBinding
import com.example.sec3_latihanaplikasi18_room.helper.DateHelper
import com.example.sec3_latihanaplikasi18_room.helper.ViewModelfactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteAddUpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note : Note? = null

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private var _activityNoteAddUpdateBinding: ActivityNoteAddUpdateBinding? = null
    private val binding get() = _activityNoteAddUpdateBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityNoteAddUpdateBinding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@NoteAddUpdateActivity)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        }
        else {
            note = Note()
        }
        val actionBarTitle : String
        val btnTitle : String

        if(isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note ->
                    binding.edtTitle.setText(note.title)
                    binding.edtDescription.setText(note.description)
                }
            }
        }
        else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }
                description.isEmpty() -> binding.edtDescription.error = getString(R.string.empty)

                else -> {

                    note.let { note->
                        note?.title = title
                        note?.description = description
                    }

                    if (isEdit) {
                        CoroutineScope(Dispatchers.IO).launch {
                            noteAddUpdateViewModel.update(note as Note)
                            withContext(Dispatchers.Main) {
                                showToast(getString(R.string.changed))
                            }
                        }
                    }
                    else {
                        note.let {
                            it?.date = DateHelper.getCurrentDate()
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            noteAddUpdateViewModel.insert(note as Note)
                            withContext(Dispatchers.Main) {
                                showToast(getString(R.string.added))
                            }
                        }
                        finish()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogTitle = getString(R.string.delete)
            dialogMessage = getString(R.string.message_delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    CoroutineScope(Dispatchers.IO).launch {
                        noteAddUpdateViewModel.delete(note as Note)

                        withContext(Dispatchers.Main) {
                            if (!isDialogClose) {
                                noteAddUpdateViewModel
                            }
                        }
                    }
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) {dialog,_ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityNoteAddUpdateBinding = null
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun obtainViewModel(activity : AppCompatActivity) : NoteAddUpdateViewModel {
        val factory = ViewModelfactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

}