package com.poly.poly_sender_android.common

import android.util.Log

class Logger {
    private var className = UNKNOWN_CLASS

    fun connect(where: Class<*>) {
        Log.i("TEST1", "Connect to ${where.simpleName}")
        className = where.simpleName
    }

    fun log(what: String) {
        Log.d(TAG_LOGGER + className, what)
    }

    private companion object {
        const val UNKNOWN_CLASS = "UnknownClass"
        const val TAG_LOGGER = "LOGGER   |   "
    }
}