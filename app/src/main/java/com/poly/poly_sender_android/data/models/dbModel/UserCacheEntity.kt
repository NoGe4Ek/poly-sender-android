package com.poly.poly_sender_android.data.models.dbModel

import androidx.room.*
import com.poly.poly_sender_android.data.database.AppDatabase
import com.poly.poly_sender_android.data.models.domainModel.Role

@Entity(tableName = AppDatabase.DATABASE_NAME)
data class UserCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "status")
    val status: Boolean,

    @ColumnInfo(name = "idStaff")
    val idStaff: String,

    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "fullName")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "roles")
    val roles: String,
)
