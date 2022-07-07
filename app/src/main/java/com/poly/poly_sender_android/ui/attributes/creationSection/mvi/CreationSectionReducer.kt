package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import androidx.navigation.Navigation
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.attributes.creationSection.CreationSectionFragmentDirections
import com.poly.poly_sender_android.util.MessageConstants

class CreationSectionReducer: Reducer<CreationSectionState, CreationSectionEffect, CreationSectionNews> {

    override fun reduce(state: CreationSectionState, effect: CreationSectionEffect): Pair<CreationSectionState?, CreationSectionNews?> {
        var reducedState: CreationSectionState? = null
        var reducedNews: CreationSectionNews? = null
        when (effect) {
            is CreationSectionEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is CreationSectionEffect.Success -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationSectionNews.Message(MessageConstants.INFO_CREATED)
                val creationSectionFragment =
                    CreationSectionFragmentDirections.actionGlobalAfterCreated()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationSectionFragment)
            }

            is CreationSectionEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationSectionNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}