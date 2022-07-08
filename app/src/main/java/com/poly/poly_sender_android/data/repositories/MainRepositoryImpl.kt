package com.poly.poly_sender_android.data.repositories

import com.poly.poly_sender_android.data.database.UserDao
import com.poly.poly_sender_android.data.models.domainModel.*
import com.poly.poly_sender_android.data.network.*
import com.poly.poly_sender_android.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
    private val createFilterResponseMapper: CreateFilterResponseMapper,
    private val cacheMapper: CacheMapper,
    private val sectionMapper: SectionMapper,
    private val sectionTemplateMapper: SectionTemplateMapper,

    private val sessionManager: SessionManager
) : MainRepository {
    override lateinit var user: User
    override suspend fun saveAuthToken(user: User) {
        sessionManager.saveAuthToken(user.token)
    }

    override suspend fun fetchAuthToken(): String {
        return sessionManager.fetchAuthToken() ?: ""
    }

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

    override suspend fun tryAutoSignIn(): User? {
        var user: User? = null
        val token = fetchAuthToken()
        if (token != "") {
            try {
                user = getLocalUser()
                retrofit.getDataAttributesCurrentStaff(CommonRequestBody(user.idStaff))
                this.user = user
            } catch (e: Exception) {
                user = null
            }
        }

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
        val attributesNE = retrofit.getDataAttributes(CommonRequestBody(user.idStaff))
        return attributeMapper.mapFromEntityList(attributesNE)
    }

    override suspend fun getAttribute(idAttribute: String): Attribute {
        val attributeNE = retrofit.getAttributeById(GetAttrByIdRequestBody(idAttribute))
        return attributeMapper.mapFromEntity(attributeNE)
    }

    override suspend fun updateAttribute(attribute: Attribute) {
        retrofit.updateAttribute(
            UpdateAttributeBody(
                idStaff = user.idStaff,
                idAttribute = attribute.id,
                name = attribute.attributeName,
                groupName = attribute.groupName,
                expression = attribute.expression,
                studentsId = attribute.students
            )
        )
    }

    override suspend fun deleteAttribute(attribute: Attribute) {
        retrofit.deleteAttribute(
            DeleteAttributeRequestBody(
                idAttribute = attribute.id,
            )
        )
    }

    override suspend fun getSections(id: String): List<Section> {
        val sectionsNE = retrofit.getSections(CommonRequestBody(user.idStaff))
        return sectionMapper.mapFromEntityList(sectionsNE)
    }

    override suspend fun getSectionTemplates(id: String): List<Section> {
        val sectionsNE = retrofit.getSectionTemplates(CommonRequestBody(user.idStaff))
        return sectionTemplateMapper.mapFromEntityList(sectionsNE)
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

    override suspend fun deleteGroupName(id: String) {
        retrofit.deleteGroupName(
            DeleteSectionRequestBody(
                idGroupAttribute = id,
            )
        )
    }

    override suspend fun getFilters(id: String): List<Filter> {
        val filtersNE = retrofit.getFilters(CommonRequestBody(id))

        return filterMapper.mapFromEntityList(filtersNE)
    }

    override suspend fun deleteFilter(filter: Filter) {
        retrofit.deleteFilter(
            DeleteFilterRequestBody(
                idFilter = filter.id,
            )
        )
    }

    override suspend fun updateFilter(filter: Filter) {
        retrofit.updateFilter(
            UpdateFilterBody(
                idStaff = user.idStaff,
                idFilter = filter.id,
                name = filter.filterName,
                mailOption = filter.mode.str,
                expression = filter.expression,
                studentsId = filter.students
            )
        )
    }

    override suspend fun createFilter(
        idStaff: String,
        name: String,
        mailOption: String,
        expression: String,
        studentsId: List<String>
    ): CreateFilterResponse {
        val createFilterResponseNE = retrofit.createFilter(
            CreateFilterBody(
                idStaff, name, mailOption, expression, studentsId
            )
        )
        return createFilterResponseMapper.mapFromEntity(createFilterResponseNE)
    }

    override suspend fun getStudents(id: String): List<Student> {
        val studentsNE = retrofit.getAllStudents(CommonRequestBody(id))
        return studentMapper.mapFromEntityList(studentsNE)
    }
}