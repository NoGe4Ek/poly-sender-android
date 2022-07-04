package com.poly.poly_sender_android

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
    //Students
    object StudentsBar : AppBar(R.menu.menu_main_students)
    object StudentsSelectedBar : AppBar(R.menu.menu_main_students_selected)
    object StudentsAttributing : AppBar(R.menu.menu_main_attributing)

    //Attributes
    object AttributesBar : AppBar(R.menu.menu_main_attributes)

    //Filters
    object FiltersBar : AppBar(R.menu.menu_main_attributes)

    //Creation
    object CreationNextBar : AppBar(R.menu.menu_main_creation_next)
    object CreationSelectionBar : AppBar(R.menu.menu_main_creation_selection)
    object CreationSelectionSelectedBar : AppBar(R.menu.menu_main_creation_selection_selected)
    object CreationApplyBar : AppBar(R.menu.menu_main_creation_apply)

    //More
    object StudentBar : AppBar(R.menu.menu_main_empty)
    object AttributeBar : AppBar(R.menu.menu_main_attribute_profile)
}