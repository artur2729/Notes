package com.example.notes

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        private lateinit var adapter: CustomAdapter
        private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val items = mutableListOf<Notes>()
            generateListOfItems(items)

            setUpListView()
            updateAdapter(items)

            setClickOpenItemDetails()
            setClickOpenSecondActivity()
        }

        private fun generateListOfItems(items: MutableList<Notes>) {
            for (item in 1..10) {
                items.add(
                    Notes(
                        item,
                        "Text01_%04x".format(item),
                        "Text02_%06x".format(item)
                    )
                )
            }
        }

        private fun setUpListView() {
            adapter = CustomAdapter(this)
            binding.itemListView.adapter = adapter
        }

        private fun updateAdapter(items: MutableList<Notes>) {
            adapter.add(items)
            adapter.add(Notes(101, "text01", "text02"))
            adapter.add(
                Notes(102, "text01", "text02"),
                Notes(103, "text01", "text02"),
                Notes(104, "text01", "text02"),
                Notes(105, "text01", "text02"),
            )
        }

        private fun setClickOpenSecondActivity() {
            binding.openSecondActivityButton.setOnClickListener {
                val intent = Intent(this, NoteDetails::class.java)
                val id = adapter.getMaxId().inc()
                intent.putExtra(MAIN_ACTIVITY_ITEM_INTENT_ID, id)

                startActivityForResult.launch(intent)
            }
        }

        private fun setClickOpenItemDetails() {
            binding.itemListView.setOnItemClickListener { adapterView, view, position, l ->
                val item: Notes = adapterView.getItemAtPosition(position) as Notes

                val itemIntent = Intent(this, NoteDetails::class.java)
                itemIntent.putExtra(MAIN_ACTIVITY_ITEM_INTENT_OBJECT, item)

                startActivityForResult.launch(itemIntent)
            }
        }

        private val startActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resul ->

                val item: Notes?

                when (resul.resultCode) {
                    NoteDetails.NOTES_DETAILS_ITEM_INTENT_RETURN_NEW -> {
                        item = getExtraFromParcelable(
                            resul.data,
                            NoteDetails.NOTES_DETAILS_ITEM_INTENT_RETURN_OBJECT
                        )

                        if (item != null) {
                            adapter.add(item)
                        }
                    }

                    NoteDetails.NOTES_DETAILS_ITEM_INTENT_RETURN_UPDATE -> {
                        item = getExtraFromParcelable(
                            resul.data,
                            NoteDetails.NOTES_DETAILS_ITEM_INTENT_RETURN_OBJECT
                        )
                        adapter.update(item)
                    }
                }
            }

        companion object {
            const val MAIN_ACTIVITY_ITEM_INTENT_OBJECT = "package com.example.notes_item_intent_id"
            const val MAIN_ACTIVITY_ITEM_INTENT_ID = "package com.example.notes_item_intent_object"
        }
    }

fun Activity.getExtraFromParcelable(resul: Intent?, keyName: String): Notes? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        resul?.getParcelableExtra(
            keyName,
            Notes::class.java
        )
    } else {
        resul?.getParcelableExtra(keyName)
    }