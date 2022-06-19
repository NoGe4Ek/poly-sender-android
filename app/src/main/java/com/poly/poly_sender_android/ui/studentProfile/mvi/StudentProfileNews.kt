package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface StudentProfileNews: News {
    data class Message(val duration: Int, val content: String): StudentProfileNews
}