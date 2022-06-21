package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.network.ApiRetrofit
import com.poly.poly_sender_android.data.network.SignInBody
import com.poly.poly_sender_android.util.StudentMapper
import com.poly.poly_sender_android.util.UserMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val retrofit: ApiRetrofit,
    private val studentMapper: StudentMapper,
    private val userMapper: UserMapper
) : MainRepository {

    override suspend fun checkSignIn(login: String, password: String): User {
        val user = retrofit.checkSignIn(SignInBody(login, password))
        return userMapper.mapFromEntity(user)
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