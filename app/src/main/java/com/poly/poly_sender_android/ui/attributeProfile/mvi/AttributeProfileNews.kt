package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface AttributeProfileNews : News {
    data class Message(val content: String) : AttributeProfileNews
}