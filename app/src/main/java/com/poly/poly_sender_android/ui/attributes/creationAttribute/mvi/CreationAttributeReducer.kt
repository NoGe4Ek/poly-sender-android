package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeParamFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection.CreationAttributeSelectionAttributingFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection.CreationAttributeSelectionFragmentDirections
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesEffect
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesNews
import com.poly.poly_sender_android.ui.students.StudentsAttributingFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect

class CreationAttributeReducer :
    Reducer<CreationAttributeState, CreationAttributeEffect, CreationAttributeNews> {

    override fun reduce(
        state: CreationAttributeState,
        effect: CreationAttributeEffect
    ): Pair<CreationAttributeState?, CreationAttributeNews?> {
        var reducedState: CreationAttributeState? = null
        var reducedNews: CreationAttributeNews? = null
        when (effect) {
            is CreationAttributeEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            CreationAttributeEffect.CreateAttributeSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message("Attribute was successfully created")
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(R.id.action_global_afterAttributeCreated)
            }
            is CreationAttributeEffect.CreateAttributeFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.RefreshStudentsSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(students = effect.students)
            }
            is CreationAttributeEffect.RefreshStudentsFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(searchAttributes = effect.attributes)
            }
            is CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            CreationAttributeEffect.ClearSearchParamSuccess -> {
                reducedState = state.copy(searchSelectedAttributes = emptySet())
                val creationAttributeSelectionFragment =
                    CreationAttributeSelectionAttributingFragmentDirections.actionCreationAttributeSelectionAttributingFragmentToCreationAttributeSelectionFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationAttributeSelectionFragment)
            }
            is CreationAttributeEffect.ClearSearchParamFailure -> {
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.UpdateSharedStorageByParamSuccess -> {
                reducedState = state.copy(
                    selectedName = effect.selectedName,
                    selectedSection = effect.selectedSection
                )
                val creationAttributeSelectionFragment =
                    CreationAttributeParamFragmentDirections.actionCreationAttributeParamFragmentToCreationAttributeSelectionFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationAttributeSelectionFragment)
            }
            is CreationAttributeEffect.UpdateSharedStorageBySelectionAttributingSuccess -> {
                reducedState = state.copy(
                    searchAttributes = effect.searchAttributes,
                    searchSelectedAttributes = effect.searchSelectedAttributes,
                    searchSelectedSection = effect.searchSelectedSearchSection
                )
            }
            is CreationAttributeEffect.UpdateSharedStorageBySelectionSuccess -> {
                reducedState = state.copy(
                    students = effect.students,
                    selectedStudents = effect.selectedStudents
                )
                val creationAttributeFragment =
                    CreationAttributeSelectionFragmentDirections.actionCreationAttributeSelectionFragmentToCreationAttributeFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationAttributeFragment)
            }
            is CreationAttributeEffect.RefreshSectionsFailure -> {
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }
            is CreationAttributeEffect.RefreshSectionsSuccess -> {
                reducedState = state.copy(sections = effect.searchSections)
            }
            is CreationAttributeEffect.DismissStudentSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.remove(effect.student)
                if (selectedStudents.isEmpty()) {
                    App.mCurrentActivity.supportActionBar?.title = state.label
                    App.appBar = AppBar.CreationSelectionBar
                    App.mCurrentActivity.invalidateOptionsMenu()
                } else {
                    App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                }

                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
            is CreationAttributeEffect.SelectStudentSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.add(effect.student)
                App.appBar = AppBar.CreationSelectionSelectedBar
                App.mCurrentActivity.invalidateOptionsMenu()
                App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
            is CreationAttributeEffect.DismissAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.remove(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is CreationAttributeEffect.SelectAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.add(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is CreationAttributeEffect.RefreshSelectedSectionSuccess -> {
                reducedState = state.copy(searchSelectedSection = effect.searchSelectedSection)
            }
        }
        return reducedState to reducedNews
    }
}