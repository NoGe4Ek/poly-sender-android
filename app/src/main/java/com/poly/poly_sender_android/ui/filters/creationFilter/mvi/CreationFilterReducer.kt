package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import android.widget.Toast
import androidx.navigation.Navigation
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.data.models.domainModel.MailingMode
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeParamFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection.CreationAttributeSelectionAttributingFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection.CreationAttributeSelectionFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterParamFragmentDirections
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterSharedViewModel
import com.poly.poly_sender_android.ui.filters.creationFilter.creationFilterSelection.CreationFilterSelectionAttributingFragmentDirections
import com.poly.poly_sender_android.ui.filters.creationFilter.creationFilterSelection.CreationFilterSelectionFragmentDirections

class CreationFilterReducer: Reducer<CreationFilterState, CreationFilterEffect, CreationFilterNews> {

    override fun reduce(state: CreationFilterState, effect: CreationFilterEffect): Pair<CreationFilterState?, CreationFilterNews?> {
        var reducedState: CreationFilterState? = null
        var reducedNews: CreationFilterNews? = null
        when (effect) {
            is CreationFilterEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            CreationFilterEffect.CreateFilterSuccess -> {
                reducedState = CreationFilterSharedViewModel.initState
                reducedNews = CreationFilterNews.Message("Filter was successfully created")
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(R.id.action_global_afterCreated)
            }
            is CreationFilterEffect.CreateFilterFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }

            is CreationFilterEffect.RefreshStudentsSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(students = effect.students)
            }
            is CreationFilterEffect.RefreshStudentsFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }

            is CreationFilterEffect.RefreshSearchingAttributesBySelectedSectionSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(searchAttributes = effect.attributes)
            }
            is CreationFilterEffect.RefreshSearchingAttributesBySelectedSectionFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }

            CreationFilterEffect.ClearSearchParamSuccess -> {
                reducedState = state.copy(searchSelectedAttributes = emptySet())
                val creationFilterSelectionFragment =
                    CreationFilterSelectionAttributingFragmentDirections.actionCreationFilterSelectionAttributingFragmentToCreationFilterSelectionFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationFilterSelectionFragment)
            }
            is CreationFilterEffect.ClearSearchParamFailure -> {
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }

            is CreationFilterEffect.UpdateSharedStorageByParamSuccess -> {
                reducedState = state.copy(
                    selectedName = effect.selectedName,
                    selectedMailingMode = effect.selectedMailingMode
                )
                val creationFilterSelectionFragment =
                    CreationFilterParamFragmentDirections.actionCreationFilterParamFragmentToCreationFilterSelectionFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationFilterSelectionFragment)
            }
            is CreationFilterEffect.UpdateSharedStorageBySelectionAttributingSuccess -> {
                reducedState = state.copy(
                    searchAttributes = effect.searchAttributes,
                    searchSelectedAttributes = effect.searchSelectedAttributes,
                    searchSelectedSection = effect.searchSelectedSearchSection
                )
            }
            is CreationFilterEffect.UpdateSharedStorageBySelectionSuccess -> {
                reducedState = state.copy(
                    students = effect.students,
                    selectedStudents = effect.selectedStudents
                )
                val creationFilterFragment =
                    CreationFilterSelectionFragmentDirections.actionCreationFilterSelectionFragmentToCreationFilterFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(creationFilterFragment)
            }
            is CreationFilterEffect.RefreshSectionsFailure -> {
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }
            is CreationFilterEffect.RefreshSectionsSuccess -> {
                reducedState = state.copy(sections = effect.searchSections)
            }
            is CreationFilterEffect.DismissStudentSuccess -> {
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
            is CreationFilterEffect.SelectStudentSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.add(effect.student)
                App.appBar = AppBar.CreationSelectionSelectedBar
                App.mCurrentActivity.invalidateOptionsMenu()
                App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
            is CreationFilterEffect.DismissAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.remove(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is CreationFilterEffect.SelectAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.add(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is CreationFilterEffect.RefreshSelectedSectionSuccess -> {
                reducedState = state.copy(searchSelectedSection = effect.searchSelectedSection)
            }
            CreationFilterEffect.ClearSharedStorageSuccess -> {
                reducedState = CreationFilterSharedViewModel.initState
            }
            is CreationFilterEffect.SetSharedStorageSuccess -> {
                reducedState = state.copy(
                    label = "Selection",
                    editableFilter = effect.filter,
                    isEdit = true,
                    isLoading = false,
                    selectedName = effect.filter.filterName,
                    selectedMailingMode = effect.filter.mode.str,
                    selectedStudents = effect.students.map{ it.id }.toSet(),
                )
            }
            is CreationFilterEffect.UpdateFilterFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationFilterNews.Message(effect.errorMessage)
            }
            CreationFilterEffect.UpdateFilterSuccess -> {
                reducedState = CreationFilterSharedViewModel.initState
                reducedNews = CreationFilterNews.Message("Filter was successfully updated")
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_main
                ).navigate(R.id.action_global_afterCreated)
            }
            is CreationFilterEffect.DismissStudentsSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.removeAll(effect.students)
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
            is CreationFilterEffect.SelectStudentsSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.addAll(effect.students)
                App.appBar = AppBar.CreationSelectionSelectedBar
                App.mCurrentActivity.invalidateOptionsMenu()
                App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
        }
        return reducedState to reducedNews
    }
}