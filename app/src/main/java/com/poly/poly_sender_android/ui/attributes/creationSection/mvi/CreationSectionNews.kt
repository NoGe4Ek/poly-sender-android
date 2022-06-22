package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.News


sealed interface CreationSectionNews: News {
    data class Message(val content: String): CreationSectionNews
}