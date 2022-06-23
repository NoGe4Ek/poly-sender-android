package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity
import com.poly.poly_sender_android.data.models.domainModel.Role
import com.poly.poly_sender_android.data.models.domainModel.User
import javax.inject.Inject

class CacheMapper @Inject constructor(): EntityMapper<UserCacheEntity, User>() {

    override fun mapFromEntity(entity: UserCacheEntity): User {
        with(entity) {
            return User(
                status = status.toBoolean(),
                idStaff = idStaff,
                token = token,
                fullName = fullName,
                email = email,
                roles = fromStringToRoles(roles)
            )
        }
    }

    override fun mapToEntity(domainModel: User): UserCacheEntity {
        with(domainModel) {
            return UserCacheEntity(
                id = 0,
                status = status.toString(),
                idStaff = idStaff,
                token = token,
                fullName = fullName,
                email = email,
                roles = fromRolesToString(roles)
            )
        }
    }

    private fun fromStringToRoles(string: String): List<Role> {
        return string.split(",").map { Role.valueOf(it) }
    }

    private fun fromRolesToString(roles: List<Role>): String {
        return roles.joinToString(separator = ",")
    }
}