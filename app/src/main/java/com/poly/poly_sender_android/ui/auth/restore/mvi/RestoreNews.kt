package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface RestoreNews: News {
    data class Message(val content: String): RestoreNews
}