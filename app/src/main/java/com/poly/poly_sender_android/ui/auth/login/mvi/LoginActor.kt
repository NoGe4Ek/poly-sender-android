package com.poly.poly_sender_android.ui.auth.login.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.MessageConstants.ERROR_STATUS_FALSE
import com.poly.poly_sender_android.util.MessageConstants.ERROR_UNKNOWN_EXCEPTION
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
                    val user = mainRepository.checkSignIn(wish.login, wish.password)

                    if (user.status) {
                        mainRepository.saveLocalUser(user)
                        mainRepository.saveAuthToken(user)
                        emit(LoginEffect.Success(user))
                    } else {
                        emit(LoginEffect.Failure(ERROR_STATUS_FALSE))
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: ERROR_UNKNOWN_EXCEPTION
                    emit(LoginEffect.Failure(errorMessage))
                }
            }
            LoginWish.TryAutoSignIn -> {
                emit(LoginEffect.Loading)
                val user = mainRepository.tryAutoSignIn()
                if (user != null) {
                    emit(LoginEffect.Success(user))
                } else {
                    emit(LoginEffect.TryAutoSignInFailure)
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}