package com.example.sec3_latihanaplikasi18_room

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sec3_latihanaplikasi18_room.database.Note
import com.example.sec3_latihanaplikasi18_room.databinding.ItemNoteBinding
import com.example.sec3_latihanaplikasi18_room.helper.NoteDiffCallback
import com.example.sec3_latihanaplikasi18_room.ui.insert.NoteAddUpdateActivity
import com.example.sec3_latihanaplikasi18_room.ui.insert.NoteAddUpdateViewModel

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    private val listNotes = ArrayList<Note>()
    fun setListNotes(listNotes : List<Note>) {
        val diffcallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffcallback)

        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(private val binding : ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(note : Note) {
            binding.apply {
                this.tvItemDate.text = note.date
                this.tvItemDescription.text = note.description
                this.tvItemTitle.text = note.title

                this.cvItemNote.setOnClickListener{
                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
}