package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.data.models.networkModel.FilterNetworkEntity
import javax.inject.Inject

class FilterMapper @Inject constructor() :
        EntityMapper<FilterNetworkEntity, Filter>() {

        override fun mapFromEntity(entity: FilterNetworkEntity): Filter {
                with(entity) {
                        return Filter(
                                id = id,
                                filterName = filterName,
                                mail = mail,
                                expression = expression ?: "",
                                type = type,
                                mode = mode,
                                created = created,
                                link = link,
                                mailCounter = mailCounter,
                                students = students,
                                studentsDTO = studentsDTO,
                                status = status,
                        )
                }
        }

        override fun mapToEntity(domainModel: Filter): FilterNetworkEntity {
                with(domainModel) {
                        return FilterNetworkEntity(
                                id = id,
                                filterName = filterName,
                                mail = mail,
                                expression = expression,
                                type = type,
                                mode = mode,
                                created = created,
                                link = link,
                                mailCounter = mailCounter,
                                students = students,
                                studentsDTO = studentsDTO,
                                status = status,
                        )
                }
        }
}