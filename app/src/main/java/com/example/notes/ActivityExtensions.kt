package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.example.notes.repository.Notes

fun Activity.getExtraFromParcelable(resul: Intent?, keyName: String): Notes? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        resul?.getParcelableExtra(
            keyName,
            Notes::class.java
        )
    } else {
        resul?.getParcelableExtra(keyName)
    }
