package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.data.models.domainModel.Role
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.models.networkModel.StudentNetworkEntity
import com.poly.poly_sender_android.data.models.networkModel.UserNetworkEntity
import javax.inject.Inject

class UserMapper @Inject constructor(): EntityMapper<UserNetworkEntity, User>() {

    override fun mapFromEntity(entity: UserNetworkEntity): User {
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

    override fun mapToEntity(domainModel: User): UserNetworkEntity {
        with(domainModel) {
            return UserNetworkEntity(
                status = status.toString(),
                idStaff = idStaff,
                token = token,
                fullName = fullName,
                email = email,
                roles = fromRolesToString(roles)
            )
        }
    }

    private fun fromStringToRoles(strings: List<String>): List<Role> {
        return strings.map { string -> Role.valueOf(string) }
    }

    private fun fromRolesToString(roles: List<Role>): List<String> {
        return roles.map { role -> role.str }
    }
}