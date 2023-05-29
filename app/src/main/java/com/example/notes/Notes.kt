package com.example.notes

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Notes(
    private val _id: Int,
    private var _name: String,
    private var _details: String,
    private val _creationDate: LocalDateTime = LocalDateTime.now(),
    private var _updateDate: LocalDateTime = LocalDateTime.now()
): Parcelable {
    @IgnoredOnParcel
    var id = this._id
        private set

    @IgnoredOnParcel
    var name: String = ""
        get():String {
            return _name
        }
        set(value) {
            field = value
            this._name = value
            this._updateDate = LocalDateTime.now()
        }

    @IgnoredOnParcel
    var details: String
        get():String {
            return _details
        }
        set(value) {
            this._details = value
            this._updateDate = LocalDateTime.now()
        }

    @IgnoredOnParcel
    val creationDate: LocalDateTime
        get() = this._creationDate

    @IgnoredOnParcel
    val updateDate: LocalDateTime
        get() = this._updateDate
}
