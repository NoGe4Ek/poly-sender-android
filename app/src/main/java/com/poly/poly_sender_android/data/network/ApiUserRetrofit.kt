package com.poly.poly_sender_android.data.network

import com.poly.poly_sender_android.data.models.networkModel.UserNetworkEntity
import retrofit2.http.GET

interface ApiUserRetrofit {

    @POST(Constants.USERS_URL)
    suspend fun get(): List<UserNetworkEntity>

}