package com.poly.poly_sender_android.ui.students.mvi

import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.students.StudentsAttributingFragmentDirections

class StudentsReducer :
    Reducer<StudentsState, StudentsEffect, StudentsNews> {

    override fun reduce(
        state: StudentsState,
        effect: StudentsEffect
    ): Pair<StudentsState?, StudentsNews?> {
        var reducedState: StudentsState? = null
        var reducedNews: StudentsNews? = null
        when (effect) {
            is StudentsEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is StudentsEffect.RefreshStudentsSuccess -> {
                reducedState = state.copy(isLoading = false, students = effect.students)
            }
            is StudentsEffect.RefreshStudentsFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }

            is StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess -> {
                reducedState = state.copy(isLoading = false, searchAttributes = effect.attributes)
            }
            is StudentsEffect.RefreshSearchingAttributesBySelectedSectionFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }

            StudentsEffect.ClearSearchParamSuccess -> {
                reducedState = state.copy(searchSelectedAttributes = emptySet())
                val studentsFragment =
                    StudentsAttributingFragmentDirections.actionStudentsAttributingFragmentToStudentsFragment()
                findNavController(App.mCurrentActivity, R.id.nav_host_fragment_content_main).navigate(studentsFragment)
            }
            is StudentsEffect.ClearSearchParamFailure -> {
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }
            is StudentsEffect.DismissStudentSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.remove(effect.student)
                if (selectedStudents.isEmpty()) {
                    App.mCurrentActivity.supportActionBar?.title = state.label
                    App.appBar = AppBar.StudentsBar
                    App.mCurrentActivity.invalidateOptionsMenu()
                } else {
                    App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                }

                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
            is StudentsEffect.SelectStudentSuccess -> {
                val selectedStudents = state.selectedStudents.toMutableSet()
                selectedStudents.add(effect.student)
                App.appBar = AppBar.StudentsSelectedBar
                App.mCurrentActivity.invalidateOptionsMenu()
                App.mCurrentActivity.supportActionBar?.title = "Selected: ${selectedStudents.size}"
                reducedState = state.copy(
                    selectedStudents = selectedStudents
                )
            }
            is StudentsEffect.DismissAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.remove(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is StudentsEffect.SelectAttributeSuccess -> {
                val selectedAttributes = state.searchSelectedAttributes.toMutableSet()
                selectedAttributes.add(effect.attribute)
                reducedState = state.copy(
                    searchSelectedAttributes = selectedAttributes
                )
            }
            is StudentsEffect.RefreshSectionsFailure -> {
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }
            is StudentsEffect.RefreshSectionsSuccess -> {
                reducedState = state.copy(searchSections = effect.searchSections)
            }
            is StudentsEffect.RefreshSelectedSectionSuccess -> {
                reducedState = state.copy(searchSelectedSection = effect.searchSelectedSection)
            }
        }
        return reducedState to reducedNews
    }
}