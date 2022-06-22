package com.poly.poly_sender_android.ui.profile.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class ProfileReducer: Reducer<ProfileState, ProfileEffect, ProfileNews> {

    override fun reduce(state: ProfileState, effect: ProfileEffect): Pair<ProfileState?, ProfileNews?> {
        var reducedState: ProfileState? = null
        var reducedNews: ProfileNews? = null
        when (effect) {

        }
        return reducedState to reducedNews
    }
}