package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface CreationFilterNews: News {
    data class Message(val content: String): CreationFilterNews
}