package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface CreationAttributeNews: News {
    data class Message(val content: String): CreationAttributeNews
}