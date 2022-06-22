package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.mvi.Actor
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
                    val students = mainRepository.getStudents(mainRepository.user.idStaff)
                    emit(CreationAttributeEffect.RefreshStudentsSuccess(students))
                    //TODO add filter students by params in AdditionalLogicClass
                    //TODO not necessary, add else branch to display empty list students. FOR ALL PLACES
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationAttributeEffect.RefreshStudentsFailure(errorMessage))
                }
            }
            CreationAttributeWish.ClearSearchParam -> {
                try {
                    val students = mainRepository.getStudents(mainRepository.user.idStaff)
                    emit(CreationAttributeEffect.RefreshStudentsSuccess(students))
                    emit(CreationAttributeEffect.ClearSearchParamSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
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
                        studentsId = wish.students.map { it.id }) //TODO we don't need to use the result?
                    emit(CreationAttributeEffect.CreateAttributeSuccess)
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(CreationAttributeEffect.CreateAttributeFailure(errorMessage))
                }
            }

            is CreationAttributeWish.RefreshSearchingAttributesBySelectedSection -> {
                try {
                    emit(CreationAttributeEffect.Loading)
                    val attributes = mainRepository.getDataAttributes(mainRepository.user.idStaff)
                    //TODO add filter attributes by section in AdditionalLogicClass
                    emit(
                        CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionSuccess(
                            attributes
                        )
                    )
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
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
        }
    }.flowOn(Dispatchers.IO)
}