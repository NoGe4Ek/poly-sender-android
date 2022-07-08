package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class LoginStore @Inject constructor(
) : Store<LoginState, LoginWish, LoginEffect, LoginNews>() {
    init {
        actor = LoginActor()
        reducer = LoginReducer()
    }
}