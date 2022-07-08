package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class FiltersStore @Inject constructor(
): Store<FiltersState, FiltersWish, FiltersEffect, FiltersNews>() {
    init {
        actor = FiltersActor()
        reducer = FiltersReducer()
    }
}