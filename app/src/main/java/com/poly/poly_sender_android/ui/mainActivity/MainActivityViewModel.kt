package com.poly.poly_sender_android.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.poly.poly_sender_android.ui.BaseViewModel
import com.poly.poly_sender_android.ui.mainActivity.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val initState = MainState(
        attributingEvent = false,
        searchEvent = false,
        selectAllEvent = false,
        dismissAllEvent = false,
        applyEvent = false,
        clearEvent = false,
        searchQuery = ""
    )
    val stateFlow = MutableStateFlow(initState)

    fun triggerAttributingEvent(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(attributingEvent = trigger)
    }
    fun triggerSearchEvent(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(searchEvent = trigger)
    }
    fun setSearchQuery(query: String) {
        stateFlow.value = stateFlow.value.copy(searchQuery = query)
    }
    fun triggerSelectAllEvent(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(selectAllEvent = trigger)
    }
    fun triggerDismissAllEvent(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(dismissAllEvent = trigger)
    }
    fun triggerApply(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(applyEvent = trigger)
    }
    fun triggerClear(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(clearEvent = trigger)
    }
}