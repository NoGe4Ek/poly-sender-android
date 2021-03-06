package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.attributes.mvi.*
import javax.inject.Inject

class CreationSectionStore @Inject constructor(
    logger: Logger
): Store<CreationSectionState, CreationSectionWish, CreationSectionEffect, CreationSectionNews>() {
    init {
        actor = CreationSectionActor()
        reducer = CreationSectionReducer()
    }
}