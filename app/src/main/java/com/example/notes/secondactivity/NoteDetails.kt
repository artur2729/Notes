package com.example.notes.secondactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notes.R
import com.example.notes.databinding.NoteDetailsBinding
import com.example.notes.mainactivity.MainActivity
import com.example.notes.repository.Notes

class NoteDetails : AppCompatActivity() {

    private lateinit var binding: NoteDetailsBinding
    private val activityViewModel: NoteDetailsVewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_details)

        binding = DataBindingUtil.setContentView(this, R.layout.note_details)
        //binding.item = getIntentExtra()
        binding.secondActivity = this

        activityViewModel.itemLiveData.observe(
            this,
            Observer { item ->
                binding.item = item
            }
        )

        activityViewModel.fetchItem(getIntentExtra())

        //getIntentExtra()
    }

    private fun getIntentExtra()=
        intent.getIntExtra(MainActivity.MAIN_ACTIVITY_ITEM_INTENT_ID, -1)

    fun onClickCloseButton(view: View) {
        finish()
    }

    fun onClickSaveButton() {
        activityViewModel.saveItem(binding.item as Notes)
        finish()

    }

    companion object {
        const val NOTE_DETAILS_INTENT_RETURN_OBJECT =
            "com.example.notes.note_details_intent_return_object"
        const val NOTE_DETAILS_SAVE_INSTANCE_STATE =
            "com.example.notes.note_details_save_instance_state"
        const val NOTE_DETAILS_FINISH_INTENT_STATUS =
            "com.example.notes.note_details_finish_intent_status"

        const val NOTE_DETAILS_INTENT_RETURN_NEW = 101
        const val NOTE_DETAILS_INTENT_RETURN_UPDATE = 102
    }
}
