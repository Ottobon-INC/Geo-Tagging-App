package com.orcalabs.hrms

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class OrcaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize OSMDroid configuration for maps
        Configuration.getInstance().userAgentValue = packageName
    }
}
