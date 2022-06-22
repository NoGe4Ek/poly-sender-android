package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface CreationSectionWish: Wish {
    data class CreateSection(val sectionName: String): CreationSectionWish
}
