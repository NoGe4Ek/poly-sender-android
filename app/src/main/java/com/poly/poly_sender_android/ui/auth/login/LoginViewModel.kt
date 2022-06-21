package com.poly.poly_sender_android.ui.auth.login

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.auth.login.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel<LoginState, LoginWish, LoginEffect, LoginNews>() {

    private val initState = LoginState(
        isLoading = false,
        password = "",
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<LoginWish?>()
    override val effectFlow = MutableSharedFlow<LoginEffect?>()
    override val newsFlow = MutableSharedFlow<LoginNews>()

    @Inject override lateinit var store: LoginStore
}