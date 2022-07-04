package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FiltersActor : Actor<FiltersState, FiltersWish, FiltersEffect>() {

    override suspend fun effect(
        state: FiltersState,
        wish: FiltersWish
    ): Flow<FiltersEffect?> = flow {
        when (wish) {
            is FiltersWish.RefreshFilters -> {
                try {
                    emit(FiltersEffect.Loading)
                    val filters = mainRepository.getFilters(mainRepository.user.idStaff).toMutableSet()
                    if (wish.query == "") {
                        emit(FiltersEffect.RefreshFiltersSuccess(filters))
                    } else {
                        if (wish.query != "") {
                            filters.removeAll { filter ->
                                !filter.filterName.contains(wish.query.toRegex())
                            }
                        }
                        emit(FiltersEffect.RefreshFiltersSuccess(filters))
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(FiltersEffect.RefreshFiltersFailure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}