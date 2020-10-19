package com.sazib.ksl.data.service

import android.app.Application
import android.util.Log
import com.sazib.ksl.data.db.AppDatabase

class App : Application() {

  companion object {
    private var mInstance: App? = null

    @Synchronized fun getInstance(): App {
      if (mInstance == null) mInstance = App()
      return mInstance as App
    }
  }

  override fun onCreate() {
    super.onCreate()
    mInstance = this

    AppDatabase.getInstance(this)

    Log.d("_sazib", "onCreate called")

  }
}