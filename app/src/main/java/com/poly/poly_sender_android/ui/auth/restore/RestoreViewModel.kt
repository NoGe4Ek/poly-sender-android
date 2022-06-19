package com.poly.poly_sender_android.ui.auth.restore

import com.poly.poly_sender_android.ui.auth.mvi.*
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginState
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginEffect
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginNews
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(): BaseViewModel<LoginState, UserDetailsWish, LoginEffect, LoginNews>() {

    private val initState = LoginState(
        user = null,
        emptyList()
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<UserDetailsWish?>()
    override val effectFlow = MutableSharedFlow<LoginEffect?>()
    override val newsFlow = MutableSharedFlow<LoginNews>()

    @Inject override lateinit var store: LoginStore
}