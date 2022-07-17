package com.poly.poly_sender_android.ui.filterProfile.mvi

import androidx.navigation.findNavController
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.util.MessageConstants

class FilterProfileReducer :
    Reducer<FilterProfileState, FilterProfileEffect, FilterProfileNews> {

    override fun reduce(
        state: FilterProfileState,
        effect: FilterProfileEffect
    ): Pair<FilterProfileState?, FilterProfileNews?> {
        var reducedState: FilterProfileState? = null
        var reducedNews: FilterProfileNews? = null
        when (effect) {
            is FilterProfileEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is FilterProfileEffect.Success -> {
                reducedState = state.copy(isLoading = false, filter = effect.filter)
            }
            is FilterProfileEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = FilterProfileNews.Message(effect.errorMessage)
            }
            is FilterProfileEffect.DeleteFilterFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = FilterProfileNews.Message(effect.errorMessage)
            }
            is FilterProfileEffect.DeleteFilterSuccess -> {
                reducedState = state.copy(isLoading = false)
                App.mCurrentActivity.findNavController(R.id.nav_host_fragment_content_main)
                    .navigateUp()
            }
            is FilterProfileEffect.GetEmailsFailure -> {
                reducedNews = FilterProfileNews.Message(effect.errorMessage)
            }
            is FilterProfileEffect.GetEmailsSuccess -> {
                reducedNews = FilterProfileNews.Message(MessageConstants.INFO_REPLIED)
            }
        }
        return reducedState to reducedNews
    }
}