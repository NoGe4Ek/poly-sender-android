package com.poly.poly_sender_android.util
/*
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.data.models.dbModel.UserCacheEntity
import com.poly.poly_sender_android.data.models.domainModel.EyeColor
import com.poly.poly_sender_android.data.models.domainModel.Fruit
import javax.inject.Inject

class CacheMapper @Inject constructor(): EntityMapper<UserCacheEntity, User>() {

    override fun mapFromEntity(entity: UserCacheEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            age = entity.age,
            company = entity.company,
            email = entity.email,
            phone = entity.phone,
            address = entity.address,
            about = entity.about,
            eyeColor = when (entity.eyeColor) {
                "brown" -> EyeColor.COLOR_BROWN
                "green" -> EyeColor.COLOR_GREEN
                else -> EyeColor.COLOR_BLUE
            },
            favoriteFruit = when (entity.favoriteFruit) {
                "apple" -> Fruit.APPLE
                "banana" -> Fruit.BANANA
                else -> Fruit.STRAWBERRY
            },
            registered = entity.registered,
            latitude = entity.latitude,
            longitude = entity.longitude,
            friends = entity.friends,
            isActive = entity.isActive,
        )
    }

    override fun mapToEntity(domainModel: User): UserCacheEntity {
        return UserCacheEntity(
            id = domainModel.id,
            name = domainModel.name,
            age = domainModel.age,
            company = domainModel.company,
            email = domainModel.email,
            phone = domainModel.phone,
            address = domainModel.address,
            about = domainModel.about,
            eyeColor = domainModel.eyeColor.str,
            favoriteFruit = domainModel.favoriteFruit.str,
            registered = domainModel.registered,
            latitude = domainModel.latitude,
            longitude = domainModel.longitude,
            friends = domainModel.friends,
            isActive = domainModel.isActive,
        )
    }
}*/