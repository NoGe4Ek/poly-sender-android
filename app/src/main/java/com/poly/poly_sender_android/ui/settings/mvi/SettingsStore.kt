package com.poly.poly_sender_android.ui.settings.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class SettingsStore @Inject constructor(
): Store<SettingsState, SettingsWish, SettingsEffect, SettingsNews>() {
    init {
        actor = SettingsActor()
        reducer = SettingsReducer()
    }
}