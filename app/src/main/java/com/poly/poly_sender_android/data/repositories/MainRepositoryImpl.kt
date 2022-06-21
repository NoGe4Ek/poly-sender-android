package com.poly.poly_sender_android.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.domainModel.RestoreResponse
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.models.networkModel.AttributeNetworkEntity
import com.poly.poly_sender_android.data.network.*
import com.poly.poly_sender_android.util.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val retrofit: ApiRetrofit,
    private val studentMapper: StudentMapper,
    private val userMapper: UserMapper,
    private val getAccessResponseMapper: GetAccessResponseMapper,
    private val restoreMapper: RestoreMapper,
    private val attributeMapper: AttributeMapper,

    override var user: User,
) : MainRepository {

    override suspend fun checkSignIn(login: String, password: String): User {
        val userNE = retrofit.checkSignIn(SignInBody(login, password))
        user = userMapper.mapFromEntity(userNE)
        return user
    }

    override suspend fun getAccess(
        firstName: String,
        lastName: String,
        patronymic: String,
        email: String,
        department: String,
        highSchool: String
    ): GetAccessResponse {
        val getAccessResponseNE = retrofit.getAccess(
            GetAccessBody(
                firstName,
                lastName,
                patronymic,
                email,
                department,
                highSchool
            )
        )

        return getAccessResponseMapper.mapFromEntity(getAccessResponseNE)
    }

    override suspend fun restorePassword(login: String): RestoreResponse {
        val restoreResponseNE = retrofit.restorePassword(RestoreBody(login))

        return restoreMapper.mapFromEntity(restoreResponseNE)
    }

    override suspend fun getDataAttributesCurrentStaff(id: String): List<Attribute> {
        val attributesNE = retrofit.getDataAttributesCurrentStaff(CommonRequestBody(id))

        return attributeMapper.mapFromEntityList(attributesNE)
    }

    fun getToken(): String? {
        return settings.getString(PREFS_TOKEN, null)
    }

    fun setToken(token: String) {
        settings.edit {
            putString(PREFS_TOKEN, token)
        }
    }

    companion object {
        private const val PREFS_NAME = "PrefsFile"
        private const val PREFS_TOKEN = "token"
        val settings: SharedPreferences = App.appContext.getSharedPreferences(PREFS_NAME, 0)

    }
}