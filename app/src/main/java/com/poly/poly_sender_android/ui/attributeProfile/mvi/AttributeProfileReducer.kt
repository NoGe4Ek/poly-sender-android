package com.poly.poly_sender_android.ui.attributeProfile.mvi

import androidx.navigation.findNavController
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer

class AttributeProfileReducer :
    Reducer<AttributeProfileState, AttributeProfileEffect, AttributeProfileNews> {

    override fun reduce(
        state: AttributeProfileState,
        effect: AttributeProfileEffect
    ): Pair<AttributeProfileState?, AttributeProfileNews?> {
        var reducedState: AttributeProfileState? = null
        var reducedNews: AttributeProfileNews? = null
        when (effect) {
            is AttributeProfileEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is AttributeProfileEffect.Success -> {
                reducedState = state.copy(isLoading = false, attribute = effect.attribute)
            }
            is AttributeProfileEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = AttributeProfileNews.Message(effect.errorMessage)
            }
            is AttributeProfileEffect.DeleteAttributeFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = AttributeProfileNews.Message(effect.errorMessage)
            }
            is AttributeProfileEffect.DeleteAttributeSuccess -> {
                reducedState = state.copy(isLoading = false)
                App.mCurrentActivity.findNavController(R.id.nav_host_fragment_content_main).navigateUp()
            }
        }
        return reducedState to reducedNews
    }
}