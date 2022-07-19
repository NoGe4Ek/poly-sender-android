package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.*
import javax.inject.Singleton

@Singleton
interface MainRepository {
    var user: User


    suspend fun saveAuthToken(user: User)
    suspend fun fetchAuthToken(): String
    suspend fun clearAuthToken()

    suspend fun saveLocalUser(user: User)
    suspend fun updateLocalUser(user: User)
    suspend fun nukeTable()
    suspend fun getLocalUser(): User

    suspend fun checkSignIn(login: String, password: String): User
    suspend fun tryAutoSignIn(): User?

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

    suspend fun getAttribute(idAttribute: String): Attribute
    suspend fun updateAttribute(attribute: Attribute)
    suspend fun deleteAttribute(attribute: Attribute)
    suspend fun createAttribute(
        idStaff: String,
        name: String,
        groupName: String,
        expression: String,
        studentsId: List<String>,
    ): CreateAttributeResponse

    suspend fun getSections(id: String): List<Section>
    suspend fun getSectionTemplates(id: String): List<Section>

    suspend fun createGroupName(id: String, groupName: String): CreateGroupResponse
    suspend fun deleteGroupName(id: String)

    suspend fun getFilters(id: String): List<Filter>

    suspend fun deleteFilter(filter: Filter)
    suspend fun updateFilter(filter: Filter)
    suspend fun createFilter(
        idStaff: String,
        name: String,
        mailOption: String,
        expression: String,
        studentsId: List<String>,
    ): CreateFilterResponse

    suspend fun getStudents(id: String): List<Student>

    suspend fun getEmails(filter: Filter)
}