package com.kacperkk.doggosapp

import android.app.Application
import com.kacperkk.doggosapp.data.AppContainer
import com.kacperkk.doggosapp.data.DefaultAppContainer

class DoggosApp: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}