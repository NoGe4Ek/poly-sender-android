package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
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
                    val students = mainRepository.getStudents(mainRepository.user.idStaff)
                    emit(StudentsEffect.RefreshStudentsSuccess(students))
                    //TODO add filter students by params in AdditionalLogicClass
                    //TODO not necessary, add else branch to display empty list students. FOR ALL PLACES
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentsEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            StudentsWish.ClearSearchParam -> {
                try {
                    val students = mainRepository.getStudents(mainRepository.user.idStaff)
                    emit(StudentsEffect.RefreshStudentsSuccess(students))
                    emit(StudentsEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentsEffect.ClearSearchParamFailure(errorMessage))
                }
            }
            is StudentsWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(StudentsEffect.Loading)
                    val attributes = mainRepository.getDataAttributes(mainRepository.user.idStaff)
                    //TODO add filter attributes by section in AdditionalLogicClass
                    emit(
                        StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                            attributes
                        )
                    )
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(
                        StudentsEffect.RefreshSearchingAttributesBySelectedSectionFailure(
                            errorMessage
                        )
                    )
                }
            }
            is StudentsWish.UpdateSharedStorageByStudents -> {
                emit(
                    StudentsEffect.UpdateSharedStorageByStudentsSuccess(
                        wish.students,
                        wish.selectedStudents
                    )
                )
            }
            is StudentsWish.UpdateSharedStorageByStudentsAttributing -> {
                emit(
                    StudentsEffect.UpdateSharedStorageByStudentsAttributingSuccess(
                        wish.searchAttributes,
                        wish.searchSelectedAttributes,
                        wish.searchSelectedSearchSection
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)
}