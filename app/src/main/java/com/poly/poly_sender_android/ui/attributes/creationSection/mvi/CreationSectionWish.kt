package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeWish

sealed interface CreationSectionWish: Wish {
    data class CreateSection(val name: String): CreationSectionWish
}
