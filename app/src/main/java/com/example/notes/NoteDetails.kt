package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteDetails : AppCompatActivity() {

    private lateinit var idEditText: TextView
    private lateinit var text01EditText: EditText
    private lateinit var text02EditText: EditText
    private lateinit var closeButton: Button
    private lateinit var saveButton: Button
    private var finishIntentStatus = SECOND_ACTIVITY_ITEM_INTENT_RETURN_UPDATE
    private lateinit var item: Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_details)

        idEditText = findViewById(R.id.idEditText)
        text01EditText = findViewById(R.id.text01EditText)
        text02EditText = findViewById(R.id.text02EditText)
        closeButton = findViewById(R.id.closeButton)
        saveButton = findViewById(R.id.saveButton)

        getIntentExtra()
        setClickListenerOfCloseButton()
        setClickListenerOfSaveButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(NOTES_DETAILS_ITEM_ID, idEditText.text.toString())
            putString(NOTES_DETAILS_ITEM_TEXT01, text01EditText.text.toString())
            putString(NOTES_DETAILS_ITEM_TEXT02, text02EditText.text.toString())
            putInt(NOTES_DETAILS_FINISH_INTENT_STATUS, finishIntentStatus)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        with(savedInstanceState) {
            idEditText.setText(this.getString(NOTES_DETAILS_ITEM_ID))
            text01EditText.setText(this.getString(NOTES_DETAILS_ITEM_TEXT01))
            text02EditText.setText(this.getString(NOTES_DETAILS_ITEM_TEXT02))
            finishIntentStatus = this.getInt(NOTES_DETAILS_FINISH_INTENT_STATUS)
        }
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(MainActivity.MAIN_ACTIVITY_ITEM_INTENT_OBJECT)) {

            item = getExtraFromParcelable(
                intent,
                MainActivity.MAIN_ACTIVITY_ITEM_INTENT_OBJECT
            ) ?: Notes(-1, "", "")

            idEditText.setText(item.id.toString())
            text01EditText.setText(item.name)
            text02EditText.setText(item.details)


        } else if (intent.hasExtra(MainActivity.MAIN_ACTIVITY_ITEM_INTENT_ID)) {

            idEditText.setText(
                intent
                    .getIntExtra(MainActivity.MAIN_ACTIVITY_ITEM_INTENT_ID, -1)
                    .toString()
            )
            finishIntentStatus = NOTES_DETAILS_ITEM_INTENT_RETURN_NEW

        } else {

            finishIntentStatus = RESULT_CANCELED
        }
    }

    private fun setClickListenerOfCloseButton() {
        closeButton.setOnClickListener {
            finish()
        }
    }

    private fun setClickListenerOfSaveButton() {
        saveButton.setOnClickListener {

            val finishIntent = Intent()

            when (finishIntentStatus) {
                NOTES_DETAILS_ITEM_INTENT_RETURN_NEW -> {
                    val item = Notes(
                        idEditText.text.toString().toInt(),
                        text01EditText.text.toString(),
                        text02EditText.text.toString()
                    )
                    finishIntent.putExtra(NOTES_DETAILS_ITEM_INTENT_RETURN_OBJECT, item)
                }

                NOTES_DETAILS_ITEM_INTENT_RETURN_UPDATE -> {

                    item.name = text01EditText.text.toString()
                    item.details = text02EditText.text.toString()

                    finishIntent.putExtra(NOTES_DETAILS_ITEM_INTENT_RETURN_OBJECT, item)
                }
            }

            if (idEditText.text.toString().toInt() < 0) {
                finishIntentStatus = RESULT_CANCELED
            }

            setResult(finishIntentStatus, finishIntent)
            finish()
        }
    }

    companion object {
        const val NOTES_DETAILS_ITEM_INTENT_RETURN_OBJECT =
            "com.example.notes.notedetails_item_intent_return_object"
        const val NOTES_DETAILS_ITEM_ID = "com.example.notes.notedetails_item_id"
        const val NOTES_DETAILS_ITEM_TEXT01 = "com.example.notes.notedetails_item_name"
        const val NOTES_DETAILS_ITEM_TEXT02 = "com.example.notes.notedetails_item_details"
        const val NOTES_DETAILS_FINISH_INTENT_STATUS =
            "com.example.notes.notedetails_finish_intent_status"

        const val NOTES_DETAILS_ITEM_INTENT_RETURN_NEW = 101
        const val NOTES_DETAILS_ITEM_INTENT_RETURN_UPDATE = 102
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
}
