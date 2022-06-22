package com.poly.poly_sender_android.ui.settings

import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.settings.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsState, SettingsWish, SettingsEffect, SettingsNews>() {

    private val initState = SettingsState(
        r = ""
    )
    override val stateFlow = MutableStateFlow(initState)
    override val wishFlow = MutableSharedFlow<SettingsWish?>()
    override val effectFlow = MutableSharedFlow<SettingsEffect?>()
    override val newsFlow = MutableSharedFlow<SettingsNews>()

    @Inject
    override lateinit var store: SettingsStore
}