package com.poly.poly_sender_android.data.models.domainModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MailingMode(val str: String) : Parcelable {
    NO_REPLY("no-reply"),
    MANUAL("manual"),
    AUTO("auto");

    companion object {
        fun convert(str: String): MailingMode {
            return when (str) {
                "no-reply" -> NO_REPLY
                "manual" -> MANUAL
                else -> AUTO
            }
        }
    }
}