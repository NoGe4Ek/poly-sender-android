package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.networkModel.AttributeNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.SectionNetworkEntity
import javax.inject.Inject

class SectionMapper @Inject constructor() :
    EntityMapper<SectionNetworkEntity, Section>() {

    override fun mapFromEntity(entity: SectionNetworkEntity): Section {
        with(entity) {
            return Section(
                id = id,
                sectionName = groupName,
                attributes = attributes,
            )
        }
    }

    override fun mapToEntity(domainModel: Section): SectionNetworkEntity {
        with(domainModel) {
            return SectionNetworkEntity(
                id = id,
                groupName = sectionName,
                attributes = attributes,
            )
        }
    }
}