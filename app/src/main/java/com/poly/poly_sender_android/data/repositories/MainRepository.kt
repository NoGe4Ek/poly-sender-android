package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.*
import javax.inject.Singleton

@Singleton
interface MainRepository {
    var user: User

    suspend fun checkSignIn(login: String, password: String): User

    suspend fun getAccess(
        firstName: String,
        lastName: String,
        patronymic: String,
        email: String,
        department: String,
        highSchool: String,
    ): GetAccessResponse

    suspend fun restorePassword(login: String): RestoreResponse

    suspend fun getDataAttributesCurrentStaff(id: String): List<Attribute>

    suspend fun getDataAttributes(id: String): List<Attribute>

    suspend fun createAttribute(
        idStaff: String,
        name: String,
        groupName: String,
        expression: String,
        studentsId: List<String>,
    ) : CreateAttributeResponse

    suspend fun createGroupName(id: String, groupName: String): CreateGroupResponse

    suspend fun getFilters(id: String): List<Filter>

    suspend fun getStudents(id: String): List<Student>
}