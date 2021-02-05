package com.sazib.ksl.data

import com.sazib.ksl.data.db.AppDbHelper
import com.sazib.ksl.data.service.App

class AppDataManager  {

  val appDbHelper: AppDbHelper = AppDbHelper(App.getInstance())

  companion object {
    private const val TAG = "AppDataManager"
    private var sInstance: AppDataManager? = null

    @Synchronized fun getInstance(): AppDataManager {

      if (sInstance == null) {
        sInstance = AppDataManager()
      }
      return sInstance as AppDataManager
    }
  }
}