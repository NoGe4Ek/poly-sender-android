package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FriendItem(

    @SerializedName("id")
    @Expose
    val id: Int
)
