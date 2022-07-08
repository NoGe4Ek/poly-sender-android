package com.poly.poly_sender_android.ui.attributeProfile.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class AttributeProfileStore @Inject constructor(
    logger: Logger
) : Store<AttributeProfileState, AttributeProfileWish, AttributeProfileEffect, AttributeProfileNews>(
) {
    init {
        actor = AttributeProfileActor()
        reducer = AttributeProfileReducer()
    }
}