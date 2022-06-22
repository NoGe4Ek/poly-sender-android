package com.poly.poly_sender_android.ui.profile.mvi

import android.content.Intent
import android.net.Uri
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileActor: Actor<ProfileState, ProfileWish, ProfileEffect>() {

    override suspend fun effect(
        state: ProfileState,
        wish: ProfileWish
    ): Flow<ProfileEffect?> = flow {
        when (wish) {
            is ProfileWish.LoginIn -> {
                emit(ProfileEffect.LoginInProcess)
            }
        }
    }.flowOn(Dispatchers.IO)
}