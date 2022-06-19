package com.poly.poly_sender_android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity
import com.poly.poly_sender_android.util.FriendsConverter

@Database(entities = [UserCacheEntity::class], version = 1)
@TypeConverters(FriendsConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "users"
    }
}