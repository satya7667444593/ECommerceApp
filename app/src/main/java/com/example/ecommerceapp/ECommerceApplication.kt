package com.example.ecommerceapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp  // ← THIS MUST BE HERE!
class ECommerceApplication : Application()