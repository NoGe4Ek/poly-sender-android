package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface CreationSectionWish: Wish {
    object SmartRefresh: CreationAttributeWish
    object RefreshFromNetwork: CreationAttributeWish
}
