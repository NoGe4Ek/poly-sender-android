package com.poly.poly_sender_android.ui.settings.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class SettingsReducer: Reducer<SettingsState, SettingsEffect, SettingsNews> {

    override fun reduce(state: SettingsState, effect: SettingsEffect): Pair<SettingsState?, SettingsNews?> {
        var reducedState: SettingsState? = null
        var reducedNews: SettingsNews? = null
        when (effect) {
            is SettingsEffect.GetUserDetailsFailure -> {
                reducedNews = SettingsNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is SettingsEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is SettingsEffect.ExternalCallFailure -> {
                reducedNews = SettingsNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is SettingsEffect.ExternalEmailFailure -> {
                reducedNews = SettingsNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is SettingsEffect.ExternalMapFailure -> {
                reducedNews = SettingsNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is SettingsEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}