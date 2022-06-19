package com.poly.poly_sender_android.data.models.dbModel

import androidx.room.*
import com.poly.poly_sender_android.data.database.AppDatabase

@Entity(tableName = AppDatabase.DATABASE_NAME)
data class UserCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: String,

    @ColumnInfo(name = "company")
    val company: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "about")
    val about: String,

    @ColumnInfo(name = "eyeColor")
    val eyeColor: String,

    @ColumnInfo(name = "favoriteFruit")
    val favoriteFruit: String,

    @ColumnInfo(name = "registered")
    val registered: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,

    @ColumnInfo(name = "longitude")
    val longitude: String,

    @ColumnInfo(name = "friends")
    val friends: List<Int>,

    @ColumnInfo(name = "isActive")
    val isActive: String
)
