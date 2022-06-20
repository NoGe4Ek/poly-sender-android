package com.poly.poly_sender_android.data.network

object Constants {

    //MAIN ADDRESS
    private const val API_ROOT = "https://poly-sender.ru:4085"
    //private const val API_ROOT = "http://localhost:4085"
    private const val API_PREFIX = ""

    //STUDENTS CONTROLLER
    private const val STUDENTS = "/students"
    private const val STUDENTS_ROOT = API_ROOT + API_PREFIX + STUDENTS

    const val URL_getAllStudents = "$STUDENTS_ROOT/getAll"

    //STAFF CONTROLLER
    private const val STAFF = "/staff"
    private const val STAFF_ROOT = API_ROOT + API_PREFIX + STAFF

    const val URL_getAllStaff = "$STAFF_ROOT/getAll"
    const val URL_changePassword = "$STAFF_ROOT/changePassword"
    const val URL_changeAccess = "$STAFF_ROOT/changeAccess"
    const val URL_getNotifications = "$STAFF_ROOT/getNotifications"
    const val URL_acceptRequest = "$STAFF_ROOT/acceptRequest"
    const val URL_rejectRequest = "$STAFF_ROOT/rejectRequest"

    //ATTRIBUTES CONTROLLER
    private const val ATTRIBUTES = "/attributes"
    private const val ATTRIBUTES_ROOT = API_ROOT + API_PREFIX + ATTRIBUTES

    const val URL_getGroupAttributes = "$ATTRIBUTES_ROOT/getGroupAttributes"
    const val URL_getAttributes = "$ATTRIBUTES_ROOT/getAttributes"
    const val URL_calculateAttribute = "$ATTRIBUTES_ROOT/calculate"
    const val URL_deleteAttribute = "$ATTRIBUTES_ROOT/deleteAttribute"
    const val URL_attributeShare = "$ATTRIBUTES_ROOT/share"
    const val URL_createAttribute = "$ATTRIBUTES_ROOT/createAttribute"
    const val URL_createGroupName = "$ATTRIBUTES_ROOT/createGroupName"
    const val URL_updateAttribute = "$ATTRIBUTES_ROOT/updateAttribute"
    const val URL_getGroupNamesCurrentStaff = "$ATTRIBUTES_ROOT/getGroupNamesCurrentStaff"
    const val URL_getGroupNames = "$ATTRIBUTES_ROOT/getGroupNames"
    const val URL_getAttributeById = "$ATTRIBUTES_ROOT/getAttributeById"
    const val URL_getAttributesCurrentStaff = "$ATTRIBUTES_ROOT/getAttributesCurrentStaff"
    const val URL_deleteGroupAttribute = "$ATTRIBUTES_ROOT/deleteGroupAttribute"

    //FILTERS CONTROLLER
    private const val FILTERS = "/filters"
    private const val FILTERS_ROOT = API_ROOT + API_PREFIX + FILTERS

    const val URL_getFiltersShort = "$FILTERS_ROOT/getFiltersShort"
    const val URL_getFilters = "$FILTERS_ROOT/getFilters"
    const val URL_getFilterById = "$FILTERS_ROOT/getFilterById"
    const val URL_deleteFilter = "$FILTERS_ROOT/deleteFilter"
    const val URL_filterShare = "$FILTERS_ROOT/share"
    const val URL_createFilter = "$FILTERS_ROOT/createFilter"
    const val URL_updateFilter = "$FILTERS_ROOT/updateFilter"
    const val URL_calculateFilter = "$FILTERS_ROOT/calculate"
    const val URL_getEmails = "$FILTERS_ROOT/getEmails"

    //AUTH CONTROLLER
    private const val AUTH = "/login"
    private const val AUTH_ROOT = API_ROOT + API_PREFIX + AUTH

    const val URL_check = "$AUTH_ROOT/check"
    const val URL_reset = "$AUTH_ROOT/reset"
    const val URL_getAccess = "$AUTH_ROOT/getAccess"

    //ADMIN CONTROLLER
    private const val ADMIN = "/admin"
    private const val ADMIN_ROOT = API_ROOT + API_PREFIX + ADMIN

    const val URL_setup = "$ADMIN_ROOT/setup"
    const val URL_change = "$ADMIN_ROOT/change"
    const val URL_reject = "$ADMIN_ROOT/reject"
    const val URL_getAccessList = "$ADMIN_ROOT/getAccessList"
    const val URL_getRoles = "$ADMIN_ROOT/getRoles"
    const val URL_update = "$ADMIN_ROOT/update"
    const val URL_getUsers = "$ADMIN_ROOT/getUsers"
    const val URL_changeRoles = "$ADMIN_ROOT/changeRoles"
    const val URL_deleteUser = "$ADMIN_ROOT/deleteUser"

    //EXCEL CONTROLLER
    private const val EXCEL = "/excel"
    private const val EXCEL_ROOT = API_ROOT + API_PREFIX + EXCEL

    const val URL_download = "$EXCEL_ROOT/download"
}