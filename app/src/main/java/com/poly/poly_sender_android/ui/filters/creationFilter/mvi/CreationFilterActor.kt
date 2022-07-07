package com.poly.poly_sender_android.ui.filters.creationFilter.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterEffect
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreationFilterActor : Actor<CreationFilterState, CreationFilterWish, CreationFilterEffect>() {

    override suspend fun effect(
        state: CreationFilterState,
        wish: CreationFilterWish
    ): Flow<CreationFilterEffect?> = flow {
        when (wish) {
            is CreationFilterWish.RefreshStudents -> {
                try {
                    emit(CreationFilterEffect.Loading)

                    val students = mutableSetOf<Student>()
                    students.addAll(mainRepository.getStudents(mainRepository.user.idStaff))

                    if (wish.searchSelectedAttributes.isEmpty() && wish.query == "")
                        emit(CreationFilterEffect.RefreshStudentsSuccess(students))
                    else {
                        val sortedStudents = mutableSetOf<Student>()
                        sortedStudents.addAll(students)
                        if (wish.searchSelectedAttributes.isNotEmpty()) {
                            sortedStudents.clear()
                            var first = true
                            val studentsUnion =
                                wish.searchSelectedAttributes.fold(setOf<String>()) { acc, attribute ->
                                    if (first) {
                                        first = false
                                        wish.searchSelectedAttributes.first().students.toSet()
                                    } else acc intersect attribute.students.toSet()
                                }
                            for (studentId in studentsUnion) {
                                for (student in students) {
                                    if (student.id == studentId) {
                                        sortedStudents.add(student)
                                    }
                                }
                            }
                        }
                        if (wish.query != "") {
                            sortedStudents.removeAll { student ->
                                !student.name.contains(wish.query.toRegex())
                            }
                        }

                        emit(CreationFilterEffect.RefreshStudentsSuccess(sortedStudents))
                    }

                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationFilterEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            CreationFilterWish.ClearSearchParam -> {
                try {
                    emit(CreationFilterEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationFilterEffect.ClearSearchParamFailure(errorMessage))
                }
            }
            is CreationFilterWish.CreateFilter -> {
                try {
                    emit(CreationFilterEffect.Loading)
                    val filter = mainRepository.createFilter(
                        idStaff = mainRepository.user.idStaff,
                        name = wish.filterName,
                        mailOption = wish.mailingMode,
                        expression = "",
                        studentsId = wish.students.toList()
                    )//TODO we don't need to use the result?
                    emit(CreationFilterEffect.CreateFilterSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationFilterEffect.CreateFilterFailure(errorMessage))
                }
            }

            is CreationFilterWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(CreationFilterEffect.Loading)
                    val attributes =
                        mainRepository.getDataAttributes(mainRepository.user.idStaff).toSet()
                    if (wish.selectedSearchSection == null) {
                        emit(
                            CreationFilterEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                                attributes
                            )
                        )
                    } else {
                        val sectionAttributes = mutableSetOf<Attribute>()
                        for (attr in attributes) {
                            if (attr.groupName == wish.selectedSearchSection.sectionName) {
                                sectionAttributes.add(attr)
                            }
                        }
                        emit(
                            CreationFilterEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                                sectionAttributes
                            )
                        )
                    }

                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        CreationFilterEffect.RefreshSearchingAttributesBySelectedSectionFailure(
                            errorMessage
                        )
                    )
                }
            }

            is CreationFilterWish.UpdateSharedStorageByParam -> {
                emit(
                    CreationFilterEffect.UpdateSharedStorageByParamSuccess(
                        wish.selectedName,
                        wish.selectedMailingMode
                    )
                )
            }
            is CreationFilterWish.UpdateSharedStorageBySelection -> {
                emit(
                    CreationFilterEffect.UpdateSharedStorageBySelectionSuccess(
                        wish.students,
                        wish.selectedStudents
                    )
                )
            }
            is CreationFilterWish.UpdateSharedStorageBySelectionAttributing -> {
                emit(
                    CreationFilterEffect.UpdateSharedStorageBySelectionAttributingSuccess(
                        wish.searchAttributes,
                        wish.searchSelectedAttributes,
                        wish.searchSelectedSearchSection
                    )
                )
            }
            CreationFilterWish.RefreshSections -> {
                try {
                    val sections = mainRepository.getSections(mainRepository.user.idStaff).toSet()
                    emit(CreationFilterEffect.RefreshSectionsSuccess(sections))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        CreationFilterEffect.RefreshSectionsFailure(
                            errorMessage
                        )
                    )
                }
            }
            is CreationFilterWish.DismissStudent -> {
                emit(CreationFilterEffect.DismissStudentSuccess(wish.student))
            }
            is CreationFilterWish.SelectStudent -> {
                emit(CreationFilterEffect.SelectStudentSuccess(wish.student))
            }
            is CreationFilterWish.DismissAttribute -> {
                emit(CreationFilterEffect.DismissAttributeSuccess(wish.attribute))
            }
            is CreationFilterWish.SelectAttribute -> {
                emit(CreationFilterEffect.SelectAttributeSuccess(wish.attribute))
            }
            is CreationFilterWish.RefreshSelectedSection -> {
                emit(CreationFilterEffect.RefreshSelectedSectionSuccess(wish.section))
            }
            CreationFilterWish.ClearSharedStorage -> {
                emit(CreationFilterEffect.ClearSharedStorageSuccess)
            }
            is CreationFilterWish.SetSharedStorage -> {
                val students = mutableSetOf<Student>()
                students.addAll(mainRepository.getStudents(mainRepository.user.idStaff))
                val selectedStudents = mutableSetOf<Student>()
                for (studentId in wish.filter.students) {
                    for (student in students) {
                        if (student.id == studentId) {
                            selectedStudents.add(student)
                        }
                    }
                }
                emit(CreationFilterEffect.SetSharedStorageSuccess(wish.filter, selectedStudents))
            }
            is CreationFilterWish.UpdateFilter -> {
                try {
                    emit(CreationFilterEffect.Loading)
                    state.editableFilter?.copy(
                        filterName = wish.filterName,
                        mode = wish.mailingMode,
                        students = wish.students.toList()
                    )?.let {
                        mainRepository.updateFilter(
                            filter = it
                        )
                        emit(CreationFilterEffect.UpdateFilterSuccess)
                    } ?: run {
                        emit(CreationFilterEffect.UpdateFilterFailure(""))
                    } //TODO we don't need to use the result?
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationFilterEffect.UpdateFilterFailure(errorMessage))
                }
            }
            is CreationFilterWish.DismissStudents -> {
                emit(CreationFilterEffect.DismissStudentsSuccess(wish.students))
            }
            is CreationFilterWish.SelectStudents -> {
                emit(CreationFilterEffect.SelectStudentsSuccess(wish.students))
            }
        }
    }.flowOn(Dispatchers.IO)
}