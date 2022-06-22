package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.Wish
import com.poly.poly_sender_android.ui.filters.FiltersSearchParam

sealed interface FiltersWish : Wish {
    data class Refresh(val filtersSearchParam: FiltersSearchParam) : FiltersWish
}
