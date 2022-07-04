package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.CreateAttributeResponse
import com.poly.poly_sender_android.data.models.domainModel.CreateFilterResponse
import com.poly.poly_sender_android.data.models.networkModel.CreateAttributeResponseNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.CreateFilterResponseNetworkEntity
import javax.inject.Inject

class CreateFilterResponseMapper @Inject constructor() :
    EntityMapper<CreateFilterResponseNetworkEntity, CreateFilterResponse>() {

    override fun mapFromEntity(entity: CreateFilterResponseNetworkEntity): CreateFilterResponse {
        with(entity) {
            return CreateFilterResponse(
                idStaff = idStaff,
                idFilter = idFilter,
                name = name,
                mailOption = mailOption,
                expression = expression,
                studentsId = studentsId
            )
        }
    }

    override fun mapToEntity(domainModel: CreateFilterResponse): CreateFilterResponseNetworkEntity {
        with(domainModel) {
            return CreateFilterResponseNetworkEntity(
                idStaff = idStaff,
                idFilter = idFilter,
                name = name,
                mailOption = mailOption,
                expression = expression,
                studentsId = studentsId
            )
        }
    }
}