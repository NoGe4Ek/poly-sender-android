package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface FiltersWish: Wish {
    object SmartRefresh: CreationFilterWish
    object RefreshFromNetwork: CreationFilterWish
}
