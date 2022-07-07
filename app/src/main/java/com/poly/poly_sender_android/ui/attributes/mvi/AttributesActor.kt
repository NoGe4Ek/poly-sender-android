package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AttributesActor : Actor<AttributesState, AttributesWish, AttributesEffect>() {

    override suspend fun effect(
        state: AttributesState,
        wish: AttributesWish
    ): Flow<AttributesEffect?> = flow {
        when (wish) {
            is AttributesWish.RefreshAttributes -> {
                try {
                    emit(AttributesEffect.Loading)
                    val attributes = mainRepository.getDataAttributesCurrentStaff(mainRepository.user.idStaff).toSet()
                    if (wish.selectedSearchSection == null && wish.query == "") {
                        emit(AttributesEffect.RefreshAttributesSuccess(attributes))
                    } else {
                        val sectionAttributes = mutableSetOf<Attribute>()
                        sectionAttributes.addAll(attributes)
                        if (wish.selectedSearchSection != null) {
                            sectionAttributes.clear()
                            for (attr in attributes) {
                                if (attr.groupName == wish.selectedSearchSection.sectionName) {
                                    sectionAttributes.add(attr)
                                }
                            }
                        }
                        if (wish.query != "") {
                            sectionAttributes.removeAll { attribute ->
                                !attribute.attributeName.contains(wish.query.toRegex())
                            }
                        }
                        emit(AttributesEffect.RefreshAttributesSuccess(sectionAttributes))
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(AttributesEffect.RefreshAttributesFailure(errorMessage))
                }
            }
            AttributesWish.RefreshSections -> {
                try {
                    val sections = mainRepository.getSections(mainRepository.user.idStaff).toSet()
                    emit(AttributesEffect.RefreshSectionsSuccess(sections))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(
                        AttributesEffect.RefreshSectionsFailure(
                            errorMessage
                        )
                    )
                }
            }
            is AttributesWish.RefreshSelectedSection -> {
                emit(AttributesEffect.RefreshSelectedSectionSuccess(wish.section))
            }
        }
    }.flowOn(Dispatchers.IO)
}