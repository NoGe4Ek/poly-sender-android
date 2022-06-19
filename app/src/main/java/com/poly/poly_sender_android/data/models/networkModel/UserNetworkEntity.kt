package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.poly.poly_sender_android.data.models.networkModel.FriendItem

data class UserNetworkEntity (
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("age")
    @Expose
    val age: String,

    @SerializedName("company")
    @Expose
    val company: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("phone")
    @Expose
    val phone: String,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("about")
    @Expose
    val about: String,

    @SerializedName("eyeColor")
    @Expose
    val eyeColor: String,

    @SerializedName("favoriteFruit")
    @Expose
    val favoriteFruit: String,

    @SerializedName("registered")
    @Expose
    val registered: String,

    @SerializedName("latitude")
    @Expose
    val latitude: String,

    @SerializedName("longitude")
    @Expose
    val longitude: String,

    @SerializedName("friends")
    @Expose
    val friends: List<FriendItem>,

    @SerializedName("isActive")
    @Expose
    val isActive: String,
)