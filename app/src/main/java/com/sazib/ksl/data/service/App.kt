package com.sazib.ksl.data.service

import android.app.Application
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.sazib.ksl.data.db.AppDatabase
import com.sazib.ksl.di.component.AppComponent
import com.sazib.ksl.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.OkHttpClient
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

  @Inject internal lateinit var okHttpClient: OkHttpClient
  @Inject internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun androidInjector(): AndroidInjector<Any> = activityDispatchingAndroidInjector

  companion object {
    private var mInstance: App? = null
    lateinit var appComponent: AppComponent

    @Synchronized fun getInstance(): App {
      if (mInstance == null) mInstance = App()
      return mInstance as App
    }
  }

  override fun onCreate() {
    super.onCreate()
    mInstance = this

    AppDatabase.getInstance(this)
    appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    appComponent.inject(this)

    //        Stetho.initializeWithDefaults(this)
    AndroidNetworking.initialize(applicationContext, okHttpClient)

    Log.d("_sazib", "onCreate called")

  }
}