package com.poly.poly_sender_android.data.database

import androidx.room.*
import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserCacheEntity)

    @Update
    suspend fun updateUser(user: UserCacheEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): UserCacheEntity

    @Query("DELETE FROM user")
    suspend fun nukeTable()
}