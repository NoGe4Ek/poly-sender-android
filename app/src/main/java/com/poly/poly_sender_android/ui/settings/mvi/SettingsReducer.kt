package com.poly.poly_sender_android.ui.settings.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class SettingsReducer: Reducer<SettingsState, SettingsEffect, SettingsNews> {

    override fun reduce(state: SettingsState, effect: SettingsEffect): Pair<SettingsState?, SettingsNews?> {
        var reducedState: SettingsState? = null
        var reducedNews: SettingsNews? = null
        when (effect) {

        }
        return reducedState to reducedNews
    }
}