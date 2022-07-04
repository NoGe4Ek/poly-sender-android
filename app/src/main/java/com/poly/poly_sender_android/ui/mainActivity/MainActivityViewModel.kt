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
        selectAllEvent = false,
        dismissAllEvent = false,
        applyEvent = false,
        clearEvent = false,
        nextEvent = false,
        editEvent = false,
        shareEvent = false,
        deleteEvent = false,
    )
    val stateFlow = MutableStateFlow(initState)
    val searchQueryStateFlow = MutableStateFlow("")

    fun triggerAttributingEvent(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(attributingEvent = trigger)
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
    fun triggerNext(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(nextEvent = trigger)
    }
    fun triggerEdit(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(editEvent = trigger)
    }
    fun triggerShare(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(shareEvent = trigger)
    }fun triggerDelete(trigger: Boolean) {
        stateFlow.value = stateFlow.value.copy(deleteEvent = trigger)
    }

}