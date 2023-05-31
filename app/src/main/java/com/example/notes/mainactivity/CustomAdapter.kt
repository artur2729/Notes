package com.example.notes.mainactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.notes.repository.Notes
import com.example.notes.databinding.NoteDetailBinding

class CustomAdapter(context: Context) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    private val list = mutableListOf<Notes>()

    fun getMaxId() = if (list.isEmpty()) {
        -1
    } else {
        list.maxBy { it.id }.id
    }

    fun add(vararg item: Notes) {
        list.addAll(item)
        notifyDataSetChanged()
    }

    fun add(items: List<Notes>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun update(item: Notes?) {
        if (item != null) {
            val index = list.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                list[index] = item
                notifyDataSetChanged()
            }
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: NoteDetailBinding

        if (view == null) {
            binding = NoteDetailBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as NoteDetailBinding
        }

        binding.item = list[position]

        return view
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}


