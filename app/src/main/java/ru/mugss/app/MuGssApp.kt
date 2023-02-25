package ru.mugss.app

import android.app.Application

class MuGssApp : Application() {
    val component by lazy { }

    override fun onCreate() {
        super.onCreate()
    }
}