package com.poly.poly_sender_android.ui.auth.restore.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class RestoreStore @Inject constructor(
    logger: Logger
): Store<RestoreState, UserDetailsWish, RestoreEffect, RestoreNews>(logger) {
    init {
        actor = RestoreActor()
        reducer = RestoreReducer()
    }
}