package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface ProfileNews: News {
    data class Message(val duration: Int, val content: String): ProfileNews
}