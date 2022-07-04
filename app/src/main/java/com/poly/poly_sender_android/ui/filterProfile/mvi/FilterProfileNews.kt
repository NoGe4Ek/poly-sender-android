package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface FilterProfileNews : News {
    data class Message(val content: String) : FilterProfileNews
}