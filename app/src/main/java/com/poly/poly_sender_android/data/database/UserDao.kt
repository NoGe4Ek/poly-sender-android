package com.poly.poly_sender_android.data.database

import androidx.room.*
import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserCacheEntity)

    @Query("SELECT * FROM users ORDER BY id ASC")
    suspend fun getUsers(): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE id in (:ids)")
    suspend fun getUsersByIds(ids: List<Int>): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Int): UserCacheEntity

    @Query("DELETE FROM users")
    suspend fun nukeTable()
}