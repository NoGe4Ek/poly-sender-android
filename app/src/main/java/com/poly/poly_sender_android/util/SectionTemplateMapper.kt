package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.networkModel.AttributeNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.SectionNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.SectionTemplateNetworkEntity
import javax.inject.Inject

class SectionTemplateMapper @Inject constructor() :
    EntityMapper<SectionTemplateNetworkEntity, Section>() {

    override fun mapFromEntity(entity: SectionTemplateNetworkEntity): Section {
        with(entity) {
            return Section(
                id = idGroupName,
                sectionName = groupName,
                attributes = emptyList(),
            )
        }
    }

    override fun mapToEntity(domainModel: Section): SectionTemplateNetworkEntity {
        with(domainModel) {
            return SectionTemplateNetworkEntity(
                idStaff = "", // if necessary
                groupName = sectionName,
                idGroupName = id
            )
        }
    }
}