package com.poly.poly_sender_android.data.network

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.networkModel.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRetrofit {

    @PublicAPI
    @POST(Constants.URL_check)
    suspend fun checkSignIn(@Body signInBody: SignInBody): UserNetworkEntity

    @PublicAPI
    @POST(Constants.URL_getAccess)
    suspend fun getAccess(@Body getAccessBody: GetAccessBody): GetAccessResponseNetworkEntity

    @PublicAPI
    @POST(Constants.URL_reset)
    suspend fun restorePassword(@Body restoreBody: RestoreBody): RestoreResponseNetworkEntity

    @POST(Constants.URL_getAttributesCurrentStaff)
    suspend fun getDataAttributesCurrentStaff(@Body commonRequestBody: CommonRequestBody): List<AttributeNetworkEntity>

    @POST(Constants.URL_getAttributes)
    suspend fun getDataAttributes(@Body commonRequestBody: CommonRequestBody): List<AttributeNetworkEntity>

    @POST(Constants.URL_getAttributeById)
    suspend fun getAttributeById(@Body getAttrByIdRequestBody: GetAttrByIdRequestBody): AttributeNetworkEntity

    @POST(Constants.URL_getGroupAttributes)
    suspend fun getSections(@Body commonRequestBody: CommonRequestBody): List<SectionNetworkEntity>

    @POST(Constants.URL_getGroupNames)
    suspend fun getSectionTemplates(@Body commonRequestBody: CommonRequestBody): List<SectionTemplateNetworkEntity>

    @POST(Constants.URL_createAttribute)
    suspend fun createAttribute(@Body createAttributeBody: CreateAttributeBody): CreateAttributeResponseNetworkEntity

    @POST(Constants.URL_updateAttribute)
    suspend fun updateAttribute(@Body updateAttributeBody: UpdateAttributeBody): UpdatedAttributeNetworkEntity

    @POST(Constants.URL_deleteAttribute)
    suspend fun deleteAttribute(@Body deleteAttributeRequestBody: DeleteAttributeRequestBody): DeleteAttributeResponseNetworkEntity

    @POST(Constants.URL_createGroupName)
    suspend fun createGroupName(@Body createGroupBody: CreateGroupBody): CreateGroupResponseNetworkEntity

    @POST(Constants.URL_deleteGroupAttribute)
    suspend fun deleteGroupName(@Body deleteGroupBody: DeleteSectionRequestBody): DeleteSectionResponseNetworkEntity

    @POST(Constants.URL_getFilters)
    suspend fun getFilters(@Body commonRequestBody: CommonRequestBody): List<FilterNetworkEntity>

    @POST(Constants.URL_deleteFilter)
    suspend fun deleteFilter(@Body deleteFilterRequestBody: DeleteFilterRequestBody): DeleteFilterResponseNetworkEntity

    @POST(Constants.URL_createFilter)
    suspend fun createFilter(@Body createFilterBody: CreateFilterBody): CreateFilterResponseNetworkEntity

    @POST(Constants.URL_updateFilter)
    suspend fun updateFilter(@Body updateFilterBody: UpdateFilterBody): UpdatedFilterNetworkEntity

    @POST(Constants.URL_getAllStudents)
    suspend fun getAllStudents(@Body commonRequestBody: CommonRequestBody): List<StudentNetworkEntity>
}