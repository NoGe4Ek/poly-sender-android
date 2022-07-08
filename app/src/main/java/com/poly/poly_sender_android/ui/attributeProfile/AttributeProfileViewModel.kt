package com.poly.poly_sender_android.ui.attributeProfile

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.attributeProfile.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AttributeProfileViewModel @Inject constructor(
    override val logger: Logger,
    override val store: AttributeProfileStore
) :
    BaseViewModel<AttributeProfileState, AttributeProfileWish, AttributeProfileEffect, AttributeProfileNews>() {

    private val initState = AttributeProfileState(
        isLoading = false,
        attribute = null,
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<AttributeProfileWish?>()
    override val effectFlow = MutableSharedFlow<AttributeProfileEffect?>()
    override val newsFlow = MutableSharedFlow<AttributeProfileNews>()
}