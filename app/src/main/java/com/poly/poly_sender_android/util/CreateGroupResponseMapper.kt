package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.CreateGroupResponse
import com.poly.poly_sender_android.data.models.networkModel.CreateGroupResponseNetworkEntity
import javax.inject.Inject

class CreateGroupResponseMapper @Inject constructor() :
    EntityMapper<CreateGroupResponseNetworkEntity, CreateGroupResponse>() {

    override fun mapFromEntity(entity: CreateGroupResponseNetworkEntity): CreateGroupResponse {
        with(entity) {
            return CreateGroupResponse(
                idStaff = idStaff,
                groupName = groupName,
            )
        }
    }

    override fun mapToEntity(domainModel: CreateGroupResponse): CreateGroupResponseNetworkEntity {
        with(domainModel) {
            return CreateGroupResponseNetworkEntity(
                idStaff = idStaff,
                groupName = groupName,
            )
        }
    }
}