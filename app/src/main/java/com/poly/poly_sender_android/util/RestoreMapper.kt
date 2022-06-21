package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.domainModel.RestoreResponse
import com.poly.poly_sender_android.data.models.networkModel.GetAccessResponseNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.RestoreResponseNetworkEntity
import javax.inject.Inject

class RestoreMapper @Inject constructor() :
    EntityMapper<RestoreResponseNetworkEntity, RestoreResponse>() {

    override fun mapFromEntity(entity: RestoreResponseNetworkEntity): RestoreResponse {
        with(entity) {
            return RestoreResponse(
                login = login,
                password = password,
            )
        }
    }

    override fun mapToEntity(domainModel: RestoreResponse): RestoreResponseNetworkEntity {
        with(domainModel) {
            return RestoreResponseNetworkEntity(
                login = login,
                password = password,
            )
        }
    }
}