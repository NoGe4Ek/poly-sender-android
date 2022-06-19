package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface AttributesNews: News {
    data class Message(val duration: Int, val content: String): CreationAttributeNews
}