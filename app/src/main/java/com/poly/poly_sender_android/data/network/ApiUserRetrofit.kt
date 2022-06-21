package com.poly.poly_sender_android.data.network

import com.poly.poly_sender_android.data.models.networkModel.UserNetworkEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRetrofit {

    @POST(Constants.URL_check)
    suspend fun checkSignIn(@Body signInBody: SignInBody): UserNetworkEntity

}