package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreationAttributeActor :
    Actor<CreationAttributeState, CreationAttributeWish, CreationAttributeEffect>() {

    override suspend fun effect(
        state: CreationAttributeState,
        wish: CreationAttributeWish
    ): Flow<CreationAttributeEffect?> = flow {
        when (wish) {
            is CreationAttributeWish.RefreshStudents -> {
                try {
                    emit(CreationAttributeEffect.Loading)

                    val students = mutableSetOf<Student>()
                    students.addAll(mainRepository.getStudents(mainRepository.user.idStaff))

                    if (wish.searchSelectedAttributes.isEmpty() && wish.query == "")
                        emit(CreationAttributeEffect.RefreshStudentsSuccess(students))
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

                        emit(CreationAttributeEffect.RefreshStudentsSuccess(sortedStudents))
                    }

                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationAttributeEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            CreationAttributeWish.ClearSearchParam -> {
                try {
                    emit(CreationAttributeEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationAttributeEffect.ClearSearchParamFailure(errorMessage))
                }
            }
            is CreationAttributeWish.CreateAttribute -> {
                try {
                    emit(CreationAttributeEffect.Loading)
                    val attribute = mainRepository.createAttribute(
                        idStaff = mainRepository.user.idStaff,
                        name = wish.attributeName,
                        groupName = wish.section,
                        expression = "",
                        studentsId = wish.students.toList()
                    ) //TODO we don't need to use the result?
                    emit(CreationAttributeEffect.CreateAttributeSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationAttributeEffect.CreateAttributeFailure(errorMessage))
                }
            }

            is CreationAttributeWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(CreationAttributeEffect.Loading)
                    val attributes =
                        mainRepository.getDataAttributes(mainRepository.user.idStaff).toSet()
                    if (wish.selectedSearchSection == null) {
                        emit(
                            CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
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
                            CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                                sectionAttributes
                            )
                        )
                    }

                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionFailure(
                            errorMessage
                        )
                    )
                }
            }

            is CreationAttributeWish.UpdateSharedStorageByParam -> {
                emit(
                    CreationAttributeEffect.UpdateSharedStorageByParamSuccess(
                        wish.selectedName,
                        wish.selectedSection
                    )
                )
            }
            is CreationAttributeWish.UpdateSharedStorageBySelection -> {
                emit(
                    CreationAttributeEffect.UpdateSharedStorageBySelectionSuccess(
                        wish.students,
                        wish.selectedStudents
                    )
                )
            }
            is CreationAttributeWish.UpdateSharedStorageBySelectionAttributing -> {
                emit(
                    CreationAttributeEffect.UpdateSharedStorageBySelectionAttributingSuccess(
                        wish.searchAttributes,
                        wish.searchSelectedAttributes,
                        wish.searchSelectedSearchSection
                    )
                )
            }
            CreationAttributeWish.RefreshSections -> {
                try {
                    val sections = mainRepository.getSectionTemplates(mainRepository.user.idStaff).toSet()
                    emit(CreationAttributeEffect.RefreshSectionsSuccess(sections))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        CreationAttributeEffect.RefreshSectionsFailure(
                            errorMessage
                        )
                    )
                }
            }
            is CreationAttributeWish.DismissStudent -> {
                emit(CreationAttributeEffect.DismissStudentSuccess(wish.student))
            }
            is CreationAttributeWish.SelectStudent -> {
                emit(CreationAttributeEffect.SelectStudentSuccess(wish.student))
            }
            is CreationAttributeWish.DismissAttribute -> {
                emit(CreationAttributeEffect.DismissAttributeSuccess(wish.attribute))
            }
            is CreationAttributeWish.SelectAttribute -> {
                emit(CreationAttributeEffect.SelectAttributeSuccess(wish.attribute))
            }
            is CreationAttributeWish.RefreshSelectedSection -> {
                emit(CreationAttributeEffect.RefreshSelectedSectionSuccess(wish.section))
            }
            CreationAttributeWish.ClearSharedStorage -> {
                emit(CreationAttributeEffect.ClearSharedStorageSuccess)
            }
            is CreationAttributeWish.SetSharedStorage -> {
                val students = mutableSetOf<Student>()
                students.addAll(mainRepository.getStudents(mainRepository.user.idStaff))
                val selectedStudents = mutableSetOf<Student>()
                for (studentId in wish.attribute.students) {
                    for (student in students) {
                        if (student.id == studentId) {
                            selectedStudents.add(student)
                        }
                    }
                }
                emit(CreationAttributeEffect.SetSharedStorageSuccess(wish.attribute, selectedStudents))
            }
            is CreationAttributeWish.UpdateAttribute -> {
                try {
                    emit(CreationAttributeEffect.Loading)
                    state.editableAttribute?.copy(
                        attributeName = wish.attributeName,
                        groupName = wish.section,
                        students = wish.students.toList()
                    )?.let {
                        mainRepository.updateAttribute(
                            attribute = it
                        )
                        emit(CreationAttributeEffect.UpdateAttributeSuccess)
                    } ?: run {
                        emit(CreationAttributeEffect.UpdateAttributeFailure(""))
                    } //TODO we don't need to use the result?
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(CreationAttributeEffect.UpdateAttributeFailure(errorMessage))
                }
            }
            is CreationAttributeWish.DismissStudents -> {
                emit(CreationAttributeEffect.DismissStudentsSuccess(wish.students))
            }
            is CreationAttributeWish.SelectStudents -> {
                emit(CreationAttributeEffect.SelectStudentsSuccess(wish.students))
            }
        }
    }.flowOn(Dispatchers.IO)
}