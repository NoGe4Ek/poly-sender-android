package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.StudentAttributesItem
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.networkModel.StudentNetworkEntity
import javax.inject.Inject

class StudentMapper @Inject constructor(): EntityMapper<StudentNetworkEntity, Student>() {

    override fun mapFromEntity(entity: StudentNetworkEntity): Student {
        with(entity) {
            return Student(
                id = id,
                name = name,
                email = email,
                attributes = mapAttributesToAttributesItem(attributes),
                isChecked = false
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

    private fun mapAttributesToAttributesItem(attributes: Map<String, List<String>>): StudentAttributesItem {
        val financing = mutableListOf<String>()
        val form = mutableListOf<String>()
        val programType = mutableListOf<String>()
        val direction = mutableListOf<String>()
        val orientation = mutableListOf<String>()
        val targetedTraining = mutableListOf<String>()
        val groupNumber = mutableListOf<String>()
        val curse = mutableListOf<String>()
        val sectionsWithAttributes = mutableMapOf<String, List<String>>()

        for((attributeName, attributeValue) in attributes) {
            when(attributeName) {
                "Финансирование" -> financing.addAll(attributeValue)
                "Форма обучения" -> form.addAll(attributeValue)
                "Тип программы" -> programType.addAll(attributeValue)
                "Направление" -> direction.addAll(attributeValue)
                "Направленность" -> orientation.addAll(attributeValue)
                "Целевое назначение" -> targetedTraining.addAll(attributeValue)
                "Номер группы" -> groupNumber.addAll(attributeValue)
                "Курс" -> curse.addAll(attributeValue)
                else -> sectionsWithAttributes[attributeName] = attributeValue
            }
        }

        return StudentAttributesItem(
            financing = financing,
            form = form,
            programType = programType,
            direction = direction,
            orientation = orientation,
            targetedTraining = targetedTraining,
            groupNumber = groupNumber,
            curse = curse,
            sectionsWithAttributes = sectionsWithAttributes,
        )
    }

    private fun mapAttributesItemToAttributes(studentAttributesItem: StudentAttributesItem):Map<String, List<String>>  {
        val attributes = mutableMapOf<String, List<String>>()

        with(studentAttributesItem) {
            attributes["Финансирование"] = financing
            attributes["Форма обучения"] = form
            attributes["Тип программы"] = programType
            attributes["Направление"] = direction
            attributes["Направленность"] = orientation
            attributes["Целевое назначение"] = targetedTraining
            attributes["Номер группы"] = groupNumber
            attributes["Курс"] = curse
            sectionsWithAttributes.forEach{ (sectionName, attributesInSection) ->
                attributes[sectionName] = attributesInSection
            }
        }

        return attributes
    }
}