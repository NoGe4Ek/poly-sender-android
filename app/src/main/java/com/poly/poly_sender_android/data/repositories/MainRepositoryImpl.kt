package com.poly.poly_sender_android.data.repositories

import android.content.SharedPreferences
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.database.UsersDao
import com.poly.poly_sender_android.util.CacheMapper
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.network.ApiUserRetrofit
import com.poly.poly_sender_android.util.NetworkMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val usersRetrofit: ApiUserRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper,
    ) : MainRepository {

    override suspend fun spGetUsers(): List<User> {
        val settings: SharedPreferences = App.appContext.getSharedPreferences(PREFS_NAME, 0)
        val users = mutableListOf<User>()
        if (settings.getBoolean(SETTINGS_FIRST_TIME, true)) {
            users.addAll(cacheAndGetUsers())
            settings.edit().putBoolean(SETTINGS_FIRST_TIME, false).apply()
        } else {
            users.addAll(getUsers())
        }

        return users
    }

    override suspend fun cacheAndGetUsers(): List<User> {
        val networkUsers = usersRetrofit.get()
        val users = networkMapper.mapFromEntityList(networkUsers)
        usersDao.addUsers(cacheMapper.mapToEntityList(users))
//        for (user in users) {
//            usersDao.addUser(cacheMapper.mapToEntity(user))
//        }
        //val cachedUsers = usersDao.getUsers()
        //val usersFromCache = cacheMapper.mapFromEntityList(cachedUsers)
        return users
    }

    override suspend fun getUsers(): List<User> {
        val cachedUsers = usersDao.getUsers()
        return cacheMapper.mapFromEntityList(cachedUsers)
    }

    override suspend fun getUsersByIds(ids: List<Int>): List<User> {
        val cachedUsers = usersDao.getUsersByIds(ids)
        return cacheMapper.mapFromEntityList(cachedUsers)
    }

    override suspend fun getUser(id: Int): User {
        val cachedUser = usersDao.getUser(id)
        return cacheMapper.mapFromEntity(cachedUser)
    }

    //Temporary solution
    override suspend fun nukeTable() {
        usersDao.nukeTable()
    }

    companion object {
        private const val PREFS_NAME = "PrefsFile"
        private const val SETTINGS_FIRST_TIME = "first_time"
    }
}