package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class CreationFilterStore @Inject constructor(
): Store<CreationFilterState, CreationFilterWish, CreationFilterEffect, CreationFilterNews>() {
    init {
        actor = CreationFilterActor()
        reducer = CreationFilterReducer()
    }
}