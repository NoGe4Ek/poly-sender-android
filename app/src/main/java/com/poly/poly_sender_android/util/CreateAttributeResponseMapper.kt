package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.CreateAttributeResponse
import com.poly.poly_sender_android.data.models.networkModel.CreateAttributeResponseNetworkEntity
import javax.inject.Inject

class CreateAttributeResponseMapper @Inject constructor() :
    EntityMapper<CreateAttributeResponseNetworkEntity, CreateAttributeResponse>() {

    override fun mapFromEntity(entity: CreateAttributeResponseNetworkEntity): CreateAttributeResponse {
        with(entity) {
            return CreateAttributeResponse(
                idStaff = idStaff,
                idAttribute = idAttribute,
                name = name,
                groupName = groupName,
                expression = expression,
                studentsId = studentsId
            )
        }
    }

    override fun mapToEntity(domainModel: CreateAttributeResponse): CreateAttributeResponseNetworkEntity {
        with(domainModel) {
            return CreateAttributeResponseNetworkEntity(
                idStaff = idStaff,
                idAttribute = idAttribute,
                name = name,
                groupName = groupName,
                expression = expression,
                studentsId = studentsId
            )
        }
    }
}