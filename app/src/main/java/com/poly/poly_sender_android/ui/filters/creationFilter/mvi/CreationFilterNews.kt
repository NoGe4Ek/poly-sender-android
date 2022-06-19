package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface CreationFilterNews: News {
    data class Message(val duration: Int, val content: String): CreationFilterNews
}