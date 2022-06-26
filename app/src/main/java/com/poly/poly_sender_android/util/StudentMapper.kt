package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.domainModel.StudentAttributesItem
import com.poly.poly_sender_android.data.models.networkModel.StudentNetworkEntity
import javax.inject.Inject

class StudentMapper @Inject constructor() : EntityMapper<StudentNetworkEntity, Student>() {

    override fun mapFromEntity(entity: StudentNetworkEntity): Student {
        with(entity) {
            return Student(
                id = id,
                name = name,
                email = email,
                attributes = mapAttributesToAttributesItem(attributes),
            )
        }
    }

    override fun mapToEntity(domainModel: Student): StudentNetworkEntity {
        with(domainModel) {
            return StudentNetworkEntity(
                id = id,
                name = name,
                email = email,
                attributes = mapAttributesItemToAttributes(attributes)
            )
        }
    }

    private fun mapAttributesToAttributesItem(attributes: Map<String, List<String>>): List<StudentAttributesItem> {
        val studentAttributes = mutableListOf<StudentAttributesItem>()

        for ((attributeName, attributeValue) in attributes) {
            studentAttributes.add(
                StudentAttributesItem(
                    attributeName = attributeName,
                    attributeValues = attributeValue,
                )
            )
        }

        return studentAttributes
    }

    private fun mapAttributesItemToAttributes(studentAttributes: List<StudentAttributesItem>): Map<String, List<String>> {
        val attributes = mutableMapOf<String, List<String>>()

        studentAttributes.forEach { attributesItem ->
            attributes[attributesItem.attributeName] = attributesItem.attributeValues
        }

        return attributes
    }
}