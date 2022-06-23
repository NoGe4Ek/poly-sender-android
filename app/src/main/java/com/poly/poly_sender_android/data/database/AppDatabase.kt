package com.poly.poly_sender_android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity

@Database(entities = [UserCacheEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "user"
    }
}