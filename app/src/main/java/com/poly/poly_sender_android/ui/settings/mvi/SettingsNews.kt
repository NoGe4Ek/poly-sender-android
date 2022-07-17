package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface SettingsNews: News {
    data class Message(val content: String): SettingsNews
}