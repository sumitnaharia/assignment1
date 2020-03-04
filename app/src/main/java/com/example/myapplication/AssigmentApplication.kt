package com.example.myapplication

import android.app.Application
import java.util.*

open class AssigmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

    }

    companion object {
        lateinit var INSTANCE: AssigmentApplication

        fun getApplicationInstance(): AssigmentApplication {
            return INSTANCE
        }
    }
}