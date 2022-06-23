package com.poly.poly_sender_android.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.database.UserDao
import com.poly.poly_sender_android.data.models.domainModel.*
import com.poly.poly_sender_android.data.network.*
import com.poly.poly_sender_android.util.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val retrofit: ApiRetrofit,
    private val studentMapper: StudentMapper,
    private val userMapper: UserMapper,
    private val getAccessResponseMapper: GetAccessResponseMapper,
    private val restoreMapper: RestoreMapper,
    private val attributeMapper: AttributeMapper,
    private val createGroupResponseMapper: CreateGroupResponseMapper,
    private val filterMapper: FilterMapper,
    private val createAttributeResponseMapper: CreateAttributeResponseMapper,
    private val cacheMapper: CacheMapper,
) : MainRepository {

    override lateinit var user: User

    override suspend fun saveLocalUser(user: User) {
        userDao.addUser(cacheMapper.mapToEntity(user))
    }

    override suspend fun updateLocalUser(user: User) {
        userDao.updateUser(cacheMapper.mapToEntity(user))
    }

    override suspend fun nukeTable() {
        userDao.nukeTable()
    }

    override suspend fun getLocalUser(): User {
        val userCE = userDao.getUser(0)
        return cacheMapper.mapFromEntity(userCE)
    }

    override suspend fun checkSignIn(login: String, password: String): User {
        val userNE = retrofit.checkSignIn(SignInBody(login, password))
        user = userMapper.mapFromEntity(userNE)
        return user
    }

    override suspend fun getAccess(
        firstName: String,
        lastName: String,
        patronymic: String,
        email: String,
        department: String,
        highSchool: String
    ): GetAccessResponse {
        val getAccessResponseNE = retrofit.getAccess(
            GetAccessBody(
                firstName,
                lastName,
                patronymic,
                email,
                department,
                highSchool
            )
        )

        return getAccessResponseMapper.mapFromEntity(getAccessResponseNE)
    }

    override suspend fun restorePassword(login: String): RestoreResponse {
        val restoreResponseNE = retrofit.restorePassword(RestoreBody(login))

        return restoreMapper.mapFromEntity(restoreResponseNE)
    }

    override suspend fun getDataAttributesCurrentStaff(id: String): List<Attribute> {
        val attributesNE = retrofit.getDataAttributesCurrentStaff(CommonRequestBody(id))

        return attributeMapper.mapFromEntityList(attributesNE)
    }

    override suspend fun getDataAttributes(id: String): List<Attribute> {
        val attributesNE = retrofit.getDataAttributes(CommonRequestBody(id))
        return attributeMapper.mapFromEntityList(attributesNE)
    }

    override suspend fun createAttribute(
        idStaff: String,
        name: String,
        groupName: String,
        expression: String,
        studentsId: List<String>
    ): CreateAttributeResponse {
        val createAttributeResponseNE = retrofit.createAttribute(
            CreateAttributeBody(
                idStaff, name, groupName, expression, studentsId
            )
        )
        return createAttributeResponseMapper.mapFromEntity(createAttributeResponseNE)
    }

    override suspend fun createGroupName(id: String, groupName: String): CreateGroupResponse {
        val createGroupResponseNE = retrofit.createGroupName(CreateGroupBody(id, groupName))
        return createGroupResponseMapper.mapFromEntity(createGroupResponseNE)
    }

    override suspend fun getFilters(id: String): List<Filter> {
        val filtersNE = retrofit.getFilters(CommonRequestBody(id))

        return filterMapper.mapFromEntityList(filtersNE)
    }

    override suspend fun getStudents(id: String): List<Student> {
        val studentsNE = retrofit.getAllStudents(CommonRequestBody(id))
        return studentMapper.mapFromEntityList(studentsNE)
    }

    fun getToken(): String? {
        return settings.getString(PREFS_TOKEN, null)
    }

    fun setToken(token: String) {
        settings.edit {
            putString(PREFS_TOKEN, token)
        }
    }

    companion object {
        private const val PREFS_NAME = "PrefsFile"
        private const val PREFS_TOKEN = "token"
        val settings: SharedPreferences = App.appContext.getSharedPreferences(PREFS_NAME, 0)

    }
}