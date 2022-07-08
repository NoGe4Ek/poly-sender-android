package com.poly.poly_sender_android.ui.auth.register.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class RegisterStore @Inject constructor(
): Store<RegisterState, RegisterWish, RegisterEffect, RegisterNews>() {
    init {
        actor = RegisterActor()
        reducer = RegisterReducer()
    }
}