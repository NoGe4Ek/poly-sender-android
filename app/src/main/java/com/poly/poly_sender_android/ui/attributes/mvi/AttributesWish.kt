package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.AttributesSearchParam

sealed interface AttributesWish: Wish {
    data class Refresh(val attributesSearchParam: AttributesSearchParam): AttributesWish
}
