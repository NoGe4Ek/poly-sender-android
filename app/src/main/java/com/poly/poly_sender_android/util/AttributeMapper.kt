package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.domainModel.RestoreResponse
import com.poly.poly_sender_android.data.models.networkModel.AttributeNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.GetAccessResponseNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.RestoreResponseNetworkEntity
import javax.inject.Inject

class AttributeMapper @Inject constructor() :
    EntityMapper<AttributeNetworkEntity, Attribute>() {

    override fun mapFromEntity(entity: AttributeNetworkEntity): Attribute {
        with(entity) {
            return Attribute(
            id = id,
            owner = owner,
            attributeName = attributeName,
            groupName = groupName,
            expression = expression,
            type = type,
            link = link,
            created = created,
            students = students,
            studentsDTO = studentsDTO,
            status = status,
            )
        }
    }

    override fun mapToEntity(domainModel: Attribute): AttributeNetworkEntity {
        with(domainModel) {
            return AttributeNetworkEntity(
                id = id,
                owner = owner,
                attributeName = attributeName,
                groupName = groupName,
                expression = expression,
                type = type,
                link = link,
                created = created,
                students = students,
                studentsDTO = studentsDTO,
                status = status,
            )
        }
    }
}