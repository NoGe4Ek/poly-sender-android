package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.*
import com.poly.poly_sender_android.data.network.SignInBody
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

    suspend fun getFilters(id: String): List<Filter>
}