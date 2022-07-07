package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StudentsActor :
    Actor<StudentsState, StudentsWish, StudentsEffect>() {

    override suspend fun effect(
        state: StudentsState,
        wish: StudentsWish
    ): Flow<StudentsEffect?> = flow {
        when (wish) {
            is StudentsWish.RefreshStudents -> {
                try {
                    emit(StudentsEffect.Loading)
                    val students = mutableSetOf<Student>()
                    students.addAll(mainRepository.getStudents(mainRepository.user.idStaff))

                    if (wish.searchSelectedAttributes.isEmpty() && wish.query == "")
                        emit(StudentsEffect.RefreshStudentsSuccess(students))
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

                        emit(StudentsEffect.RefreshStudentsSuccess(sortedStudents))
                    }


                    //TODO not necessary, add else branch to display empty list students. FOR ALL PLACES
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(StudentsEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            StudentsWish.ClearSearchParam -> {
                try {
                    emit(StudentsEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(StudentsEffect.ClearSearchParamFailure(errorMessage))
                }
            }
            is StudentsWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(StudentsEffect.Loading)

                    val attributes =
                        mainRepository.getDataAttributes(mainRepository.user.idStaff).toSet()
                    if (wish.selectedSearchSection == null) {
                        emit(
                            StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
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
                            StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                                sectionAttributes
                            )
                        )
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        StudentsEffect.RefreshSearchingAttributesBySelectedSectionFailure(
                            errorMessage
                        )
                    )
                }
            }
            is StudentsWish.DismissStudent -> {
                emit(StudentsEffect.DismissStudentSuccess(wish.student))
            }
            is StudentsWish.SelectStudent -> {
                emit(StudentsEffect.SelectStudentSuccess(wish.student))
            }
            is StudentsWish.DismissAttribute -> {
                emit(StudentsEffect.DismissAttributeSuccess(wish.attribute))
            }
            is StudentsWish.SelectAttribute -> {
                emit(StudentsEffect.SelectAttributeSuccess(wish.attribute))
            }
            is StudentsWish.RefreshSections -> {
                try {
                    val sections = mainRepository.getSections(mainRepository.user.idStaff).toSet()
                    emit(StudentsEffect.RefreshSectionsSuccess(sections))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        StudentsEffect.RefreshSectionsFailure(
                            errorMessage
                        )
                    )
                }
            }
            is StudentsWish.RefreshSelectedSection -> {
                emit(StudentsEffect.RefreshSelectedSectionSuccess(wish.section))
            }
            is StudentsWish.DismissStudents -> {
                emit(StudentsEffect.DismissStudentsSuccess(wish.students))
            }
            is StudentsWish.SelectStudents -> {
                emit(StudentsEffect.SelectStudentsSuccess(wish.students))
            }
        }
    }.flowOn(Dispatchers.IO)
}