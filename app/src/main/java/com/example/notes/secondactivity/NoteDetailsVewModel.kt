package com.example.notes.secondactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.repository.Notes
import com.example.notes.repository.NoteRepository

class NoteDetailsVewModel : ViewModel() {

    private val _itemLiveData = MutableLiveData<Notes>()
    val itemLiveData: LiveData<Notes>
        get() = _itemLiveData

    fun fetchItem(itemId: Int) {

        if (_itemLiveData.value == null) {
            if (itemId > 0) {
                _itemLiveData.value = NoteRepository.instance.getItem(itemId)
            } else {
                _itemLiveData.value = Notes(-1, "", "")
            }
        }
    }
}
