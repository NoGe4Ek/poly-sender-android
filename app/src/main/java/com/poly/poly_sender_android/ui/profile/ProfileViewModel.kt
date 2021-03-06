package com.poly.poly_sender_android.ui.profile

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.profile.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    override val logger: Logger,
    override val store: ProfileStore
) :
    BaseViewModel<ProfileState, ProfileWish, ProfileEffect, ProfileNews>() {

    private val initState = ProfileState(
        isLoading = false,
        user = null,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<ProfileWish?>()
    override val effectFlow = MutableSharedFlow<ProfileEffect?>()
    override val newsFlow = MutableSharedFlow<ProfileNews>()
}