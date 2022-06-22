package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class ProfileStore @Inject constructor(
    logger: Logger
): Store<ProfileState, ProfileWish, ProfileEffect, ProfileNews>(logger) {
    init {
        actor = ProfileActor()
        reducer = ProfileReducer()
    }
}