package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.network.SignInBody
import javax.inject.Singleton

@Singleton
interface MainRepository {

    suspend fun checkSignIn(login: String, password: String): User

    suspend fun getAccess(
        firstName: String,
        lastName: String,
        patronymic: String,
        email: String,
        department: String,
        highSchool: String,
    ): GetAccessResponse
}