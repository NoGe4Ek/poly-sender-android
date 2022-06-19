package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface RegisterNews: News {
    data class Message(val duration: Int, val content: String): RegisterNews
}