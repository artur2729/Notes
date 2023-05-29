package com.example.notes.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.repository.Notes
import com.example.notes.repository.NoteRepository

class MainActivityViewModel : ViewModel() {

    private val _itemsLiveData = MutableLiveData<List<Notes>>(mutableListOf())
    val itemsLiveData: LiveData<List<Notes>>
        get() = _itemsLiveData

    fun fetchItems() {

        if (_itemsLiveData.value == null || _itemsLiveData.value?.isEmpty() == true) {
            NoteRepository.instance.addDummyListOfItems()
        }
        _itemsLiveData.value = NoteRepository.instance.items
    }
}
