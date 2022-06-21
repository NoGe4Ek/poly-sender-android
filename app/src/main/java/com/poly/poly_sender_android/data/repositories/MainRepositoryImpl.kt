package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.GetAccessResponse
import com.poly.poly_sender_android.data.models.domainModel.RestoreResponse
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.network.ApiRetrofit
import com.poly.poly_sender_android.data.network.GetAccessBody
import com.poly.poly_sender_android.data.network.RestoreBody
import com.poly.poly_sender_android.data.network.SignInBody
import com.poly.poly_sender_android.util.GetAccessResponseMapper
import com.poly.poly_sender_android.util.RestoreMapper
import com.poly.poly_sender_android.util.StudentMapper
import com.poly.poly_sender_android.util.UserMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val retrofit: ApiRetrofit,
    private val studentMapper: StudentMapper,
    private val userMapper: UserMapper,
    private val getAccessResponseMapper: GetAccessResponseMapper,
    private val restoreMapper: RestoreMapper,
) : MainRepository {

    override suspend fun checkSignIn(login: String, password: String): User {
        val userNE = retrofit.checkSignIn(SignInBody(login, password))
        return userMapper.mapFromEntity(userNE)
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

    companion object {
        private const val PREFS_NAME = "PrefsFile"
        private const val PREFS_TOKEN = "token"
        //val settings: SharedPreferences = App.appContext.getSharedPreferences(PREFS_NAME, 0)
        //if (settings.getBoolean(SETTINGS_FIRST_TIME, true)) {
        //            users.addAll(cacheAndGetUsers())
        //            settings.edit().putBoolean(SETTINGS_FIRST_TIME, false).apply()
        //        } else {
        //            users.addAll(getUsers())
        //        }
    }
}