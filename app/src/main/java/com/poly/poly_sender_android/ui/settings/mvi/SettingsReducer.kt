package com.poly.poly_sender_android.ui.settings.mvi

import android.content.Intent
import android.widget.Toast
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.auth.AuthActivity
import com.poly.poly_sender_android.ui.mainActivity.MainActivity

class SettingsReducer: Reducer<SettingsState, SettingsEffect, SettingsNews> {

    override fun reduce(state: SettingsState, effect: SettingsEffect): Pair<SettingsState?, SettingsNews?> {
        var reducedState: SettingsState? = null
        var reducedNews: SettingsNews? = null
        when (effect) {
            is SettingsEffect.LogoutFailure -> {
                reducedNews = SettingsNews.Message(effect.errorMessage)
            }
            SettingsEffect.LogoutSuccess -> {
                val intent = Intent(App.appContext, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                App.appContext.startActivity(intent)
                App.mCurrentActivity.finish()
            }
        }
        return reducedState to reducedNews
    }
}