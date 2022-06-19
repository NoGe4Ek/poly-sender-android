package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.models.domainModel.User
import javax.inject.Singleton

@Singleton
interface MainRepository {
    suspend fun spGetUsers(): List<User>

    suspend fun cacheAndGetUsers(): List<User>

    suspend fun getUsers(): List<User>

    suspend fun getUsersByIds(ids: List<Int>): List<User>

    suspend fun getUser(id: Int): User

    suspend fun nukeTable()
}