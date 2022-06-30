package com.poly.poly_sender_android

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
        lateinit var mCurrentActivity: AppCompatActivity
        var appBar: AppBar = AppBar.StudentsBar
    }
}

sealed class AppBar(val res: Int) {
    object StudentsBar : AppBar(R.menu.menu_main)
    object StudentsSelectedBar : AppBar(R.menu.menu_main_selected)
    object StudentsAttributing : AppBar(R.menu.menu_main_attributing)
    object AttributesBar : AppBar(R.menu.menu_main)
    object FiltersBar : AppBar(R.menu.menu_main)
}