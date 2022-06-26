package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.w3c.dom.Attr

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
                    if (wish.searchSelectedAttributes.isNotEmpty()) {

                        var first = true
                        val studentsUnion =
                            wish.searchSelectedAttributes.fold(setOf<String>()) { acc, attribute ->
                                if (first) {
                                    first = false
                                    wish.searchSelectedAttributes.first().students.toSet()
                                } else acc intersect attribute.students.toSet()
                            }


                        val sortedStudents = mutableSetOf<Student>()
                        for (studentId in studentsUnion) {
                            for (student in students) {
                                if (student.id == studentId) {
                                    sortedStudents.add(student)
                                }
                            }
                        }
                        emit(StudentsEffect.RefreshStudentsSuccess(sortedStudents))
                    } else {
                        emit(StudentsEffect.RefreshStudentsSuccess(students))
                    }


                    //TODO not necessary, add else branch to display empty list students. FOR ALL PLACES
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentsEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            StudentsWish.ClearSearchParam -> {
                try {
                    emit(StudentsEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentsEffect.ClearSearchParamFailure(errorMessage))
                }
            }
            is StudentsWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(StudentsEffect.Loading)
                    val attributes = mainRepository.getDataAttributes(mainRepository.user.idStaff).toSet()
                    if (wish.selectedSearchSection != "") {
                        //TODO add filter attributes by section in AdditionalLogicClass
                    } else {
                        emit(
                            StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                                attributes
                            )
                        )
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(
                        StudentsEffect.RefreshSearchingAttributesBySelectedSectionFailure(
                            errorMessage
                        )
                    )
                }
            }
            StudentsWish.FetchLocalUser -> {
                mainRepository.getLocalUser()
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
        }
    }.flowOn(Dispatchers.IO)
}