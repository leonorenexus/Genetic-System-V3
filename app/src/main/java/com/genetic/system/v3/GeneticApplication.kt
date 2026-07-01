package com.genetic.system.v3

import android.app.Application

class GeneticApplication : Application() {
    companion object {
        lateinit var instance: GeneticApplication
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
