package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.networkModel.GetAccessResponseNetworkEntity
import javax.inject.Inject

class GetAccessResponseMapper @Inject constructor() :
    EntityMapper<GetAccessResponseNetworkEntity, GetAccessResponse>() {

    override fun mapFromEntity(entity: GetAccessResponseNetworkEntity): GetAccessResponse {
        with(entity) {
            return GetAccessResponse(
                status = when (status) {
                    "success" -> true
                    else -> false
                },
            )
        }
    }

    override fun mapToEntity(domainModel: GetAccessResponse): GetAccessResponseNetworkEntity {
        with(domainModel) {
            return GetAccessResponseNetworkEntity(
                status = if (status) "success" else "failure" //TODO
            )
        }
    }
}