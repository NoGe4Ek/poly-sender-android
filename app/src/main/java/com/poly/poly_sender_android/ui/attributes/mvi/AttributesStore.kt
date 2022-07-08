package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class AttributesStore @Inject constructor(
    logger: Logger
): Store<AttributesState, AttributesWish, AttributesEffect, AttributesNews>() {
    init {
        actor = AttributesActor()
        reducer = AttributesReducer()
    }
}