package com.poly.poly_sender_android.data.network

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.networkModel.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRetrofit {

    @POST(Constants.URL_check)
    suspend fun checkSignIn(@Body signInBody: SignInBody): UserNetworkEntity

    @POST(Constants.URL_getAccess)
    suspend fun getAccess(@Body getAccessBody: GetAccessBody): GetAccessResponseNetworkEntity

    @POST(Constants.URL_reset)
    suspend fun restorePassword(@Body restoreBody: RestoreBody): RestoreResponseNetworkEntity

    @POST(Constants.URL_getAttributesCurrentStaff)
    suspend fun getDataAttributesCurrentStaff(@Body commonRequestBody: CommonRequestBody): List<AttributeNetworkEntity>

    @POST(Constants.URL_getAttributes)
    suspend fun getDataAttributes(@Body commonRequestBody: CommonRequestBody): List<AttributeNetworkEntity>

    @POST(Constants.URL_getAttributes)
    suspend fun createAttribute(@Body createAttributeBody: CreateAttributeBody): CreateAttributeResponseNetworkEntity

    @POST(Constants.URL_createGroupName)
    suspend fun createGroupName(@Body createGroupBody: CreateGroupBody): CreateGroupResponseNetworkEntity

    @POST(Constants.URL_getFilters)
    suspend fun getFilters(@Body commonRequestBody: CommonRequestBody) : List<FilterNetworkEntity>

    @POST(Constants.URL_getAllStudents)
    suspend fun getAllStudents(@Body commonRequestBody: CommonRequestBody) : List<StudentNetworkEntity>
}