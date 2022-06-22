package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class FiltersStore @Inject constructor(
    logger: Logger
): Store<FiltersState, FiltersWish, FiltersEffect, FiltersNews>(logger) {
    init {
        actor = FiltersActor()
        reducer = FiltersReducer()
    }
}