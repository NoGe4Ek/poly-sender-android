package com.poly.poly_sender_android.data.models.domainModel

import com.poly.testwaveaccess.R

data class User (
    val id: Int,
    val name: String,
    val age: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val eyeColor: EyeColor,
    val favoriteFruit: Fruit,
    val registered: String,
    val latitude: String,
    val longitude: String,
    val friends: List<Int>,
    val isActive: String,
)

enum class EyeColor(val id: Int, val str: String) {
    COLOR_BROWN(R.drawable.oval_eye_brown, "brown"),
    COLOR_GREEN(R.drawable.oval_eye_green, "green"),
    COLOR_BLUE(R.drawable.oval_eye_blue, "blue")
}
enum class Fruit(val id: Int, val str: String) {
    APPLE(R.drawable.ic_apple, "apple"),
    BANANA(R.drawable.ic_banana, "banana"),
    STRAWBERRY(R.drawable.ic_strawberry, "strawberry")
}