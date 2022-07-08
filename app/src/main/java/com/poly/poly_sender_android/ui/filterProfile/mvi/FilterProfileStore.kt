package com.poly.poly_sender_android.ui.filterProfile.mvi

import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.mvi.Store
import javax.inject.Inject

class FilterProfileStore @Inject constructor(
) : Store<FilterProfileState, FilterProfileWish, FilterProfileEffect, FilterProfileNews>(){
    init {
        actor = FilterProfileActor()
        reducer = FilterProfileReducer()
    }
}