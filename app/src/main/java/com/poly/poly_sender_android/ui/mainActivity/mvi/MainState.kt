package com.poly.poly_sender_android.ui.mainActivity.mvi

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.mvi.State
import com.poly.poly_sender_android.ui.filters.FiltersSearchParam
import kotlinx.coroutines.flow.MutableStateFlow

data class MainState(
    val attributingEvent: Boolean,
    val searchEvent: Boolean,
    val selectAllEvent: Boolean,
    val dismissAllEvent: Boolean,
    val applyEvent: Boolean,
    val clearEvent: Boolean,
    val searchQuery: String
) : State
