package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class LoginStore @Inject constructor(
    logger: Logger
): Store<LoginState, UserDetailsWish, LoginEffect, LoginNews>(logger) {
    init {
        actor = LoginActor()
        reducer = LoginReducer()
    }
}