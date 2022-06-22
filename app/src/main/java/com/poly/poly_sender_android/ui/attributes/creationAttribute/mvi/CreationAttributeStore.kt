package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class CreationAttributeStore @Inject constructor(
    logger: Logger
): Store<CreationAttributeState, CreationAttributeWish, CreationAttributeEffect, CreationAttributeNews>(logger) {
    init {
        actor = CreationAttributeActor()
        reducer = CreationAttributeReducer()
    }
}