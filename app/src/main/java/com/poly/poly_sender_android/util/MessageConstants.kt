package com.poly.poly_sender_android.util

import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R

object MessageConstants {

    //Errors
    val ERROR_EMPTY_FILL = App.appContext.getString(R.string.empty_field_error)
    val ERROR_STATUS_FALSE = App.appContext.getString(R.string.status_false)
    val ERROR_UNKNOWN_EXCEPTION = App.appContext.getString(R.string.unknown_exception)

    //Message
    val INFO_CREATED = App.appContext.getString(R.string.create_success)
    val INFO_UPDATED = App.appContext.getString(R.string.update_success)
    val INFO_DELETED = App.appContext.getString(R.string.delete_success)
    val INFO_REPLIED = App.appContext.getString(R.string.reply_success)

    //AppHeaders
    val LABEL_SELECTED = App.appContext.getString(R.string.label_selected)
}