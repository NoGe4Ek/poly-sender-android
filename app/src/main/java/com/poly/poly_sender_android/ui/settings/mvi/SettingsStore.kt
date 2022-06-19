package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class SettingsStore @Inject constructor(
    logger: Logger
): Store<SettingsState, UserDetailsWish, SettingsEffect, SettingsNews>(logger) {
    init {
        actor = SettingsActor()
        reducer = SettingsReducer()
    }
}