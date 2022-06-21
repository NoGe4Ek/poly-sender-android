package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginActor : Actor<LoginState, LoginWish, LoginEffect>() {

    override suspend fun effect(
        state: LoginState,
        wish: LoginWish
    ): Flow<LoginEffect?> = flow {
        when (wish) {
            is LoginWish.SignIn -> {
                try {
                    emit(LoginEffect.Loading)
                    val user = mainRepository.checkSignIn(wish.email, wish.password)
                    emit(LoginEffect.Success(user))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(LoginEffect.Failure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}