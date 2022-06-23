package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.mvi.News
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews


sealed interface StudentsNews: News {
    data class Message(val content: String): StudentsNews
}