package com.example.notes.repository

class NoteRepository {

    val items = mutableListOf<Notes>()

    fun getItem(id: Int) = items.find { it.id == id }

    fun addItem(item: Notes) {
        var newId = 1

        if (!items.isEmpty()) {
            newId = items.maxBy { it.id }.id.inc()
        }

        val newItem = Notes(_id = newId, item.name, item.details)
        items.add(newItem)
    }

    fun updateItem(item: Notes?) {
        if (item != null) {
            val index = items.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                items[index] = item
            }
        }
    }

    fun addDummyListOfItems() {
        items.addAll(generateListOfItems())
    }

    fun getSearchResults(query: String): List<Notes> {
        return items.filter { it.name.contains(query, ignoreCase = true) }
    }

    private fun generateListOfItems(): List<Notes> {
        val list = mutableListOf<Notes>()

        for (number in 1..20) {
            val item = Notes(
                number,
                "dummy text01: $number",
                "dummy text02: ${(1..100).random()}"
            )
            list.add(item)
        }

        return list
    }

    companion object {
        val instance: NoteRepository = NoteRepository()
    }
}