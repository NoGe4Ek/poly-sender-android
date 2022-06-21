package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.attributes.SearchParam

sealed interface AttributesWish: Wish {
    data class Refresh(val searchParam: SearchParam): AttributesWish
}
