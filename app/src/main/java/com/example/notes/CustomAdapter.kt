package com.example.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.notes.databinding.ItemBinding

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

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun remove(vararg item: Notes) {
        list.removeAll(item)
        notifyDataSetChanged()
    }

    fun remove(items: List<Notes>) {
        list.removeAll(items)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val binding: ItemBinding

        if (view == null) {
            binding = ItemBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ItemBinding
        }

        binding.idTextView.text = list[position].id.toString()
        binding.text01TextView.text = list[position].name
        binding.text02TextView.text = list[position].details
        binding.creationDateTextView.text = list[position].creationDate.toString()
        binding.updateDateTextView.text = list[position].updateDate.toString()

        return view
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}


