package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface LoginNews: News {
    data class Message(val content: String): LoginNews
}