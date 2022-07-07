package com.poly.poly_sender_android.common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun TextView.string() = text.toString()

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}

fun Context.copyToClipboard(text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label",text)
    clipboard.setPrimaryClip(clip)
}