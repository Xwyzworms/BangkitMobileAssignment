package com.example.sec3_latihanaplikasi17_catatanringan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sec3_latihanaplikasi17_catatanringan.databinding.ItemNoteBinding
import com.example.sec3_latihanaplikasi17_catatanringan.entity.Note

class NoteAdapter(private val onItemClickCallback : OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    var listNotes = ArrayList<Note>()
    set(listNotes) {
        if (listNotes.size > 0) {
            this.listNotes.clear()
        }

        this.listNotes.addAll(listNotes)
    }

    fun addItem(note : Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size -1)
    }
    fun updateItem(position : Int, note:Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    inner class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note : Note) {
            binding.tvItemDate.text = note.date
            binding.tvItemDescription.text = note.description
            binding.tvItemTitle.text = note.title
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemclicked(note,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
    interface OnItemClickCallback {
        fun onItemclicked(selectedNote : Note?, position: Int)
    }
}