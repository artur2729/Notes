package com.example.notes.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.notes.secondactivity.NoteDetails
import com.example.notes.repository.Notes
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter
    private lateinit var binding: ActivityMainBinding
    private val activityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

        setUpListView()

        activityViewModel.itemsLiveData.observe(
            this,
            Observer { listOfItems ->
                adapter.add(listOfItems)
            }
        )

        setClickOpenItemDetails()

        binding.searchEditText.doAfterTextChanged { activityViewModel.searchNotes(binding.searchEditText.text.toString()) }
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.fetchItems()
    }

    private fun setUpListView() {
        adapter = CustomAdapter(this)
        binding.itemListView.adapter = adapter
    }

    fun onClickButtonOpenNoteDetails(view: View) {
        startActivity(Intent(this, NoteDetails::class.java))
    }

    private fun setClickOpenItemDetails() {
        binding.itemListView.setOnItemClickListener { adapterView, view, position, l ->
            val item: Notes = adapterView.getItemAtPosition(position) as Notes

            val itemIntent = Intent(this, NoteDetails::class.java)
            itemIntent.putExtra(MAIN_ACTIVITY_ITEM_INTENT_ID, item.id)

            startActivity(itemIntent)
        }
    }


    companion object {
        const val MAIN_ACTIVITY_ITEM_INTENT_ID = "package com.example.notes_item_intent_object"
    }
}