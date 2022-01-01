package com.sazib.ksl.data

import com.sazib.ksl.data.db.AppDataHelper
import com.sazib.ksl.data.service.App

class AppDataManager  {

  val appDbHelper: AppDataHelper = AppDataHelper(App.getInstance())

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