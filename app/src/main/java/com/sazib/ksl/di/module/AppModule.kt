package com.sazib.ksl.di.module

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.sazib.ksl.BuildConfig
import com.sazib.ksl.data.api.ApiHeader
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.api.AppApiHelper
import com.sazib.ksl.data.pref.AppPreferencesHelper
import com.sazib.ksl.data.pref.PreferencesHelper
import com.sazib.ksl.di.AppKey
import com.sazib.ksl.di.PackageName
import com.sazib.ksl.di.PreferenceInfo
import com.sazib.ksl.di.VersionName
import com.sazib.ksl.utils.AppConstants
import com.sazib.ksl.utils.CertSHA1
import com.sazib.ksl.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

  @Provides @Singleton internal fun provideContext(application: Application): Context = application

  @Provides @AppKey
  internal fun provideAppKey(context: Context): String =
    CertSHA1.getCertificateSHA1(context.applicationContext)

  @Provides @PackageName internal fun providePackageName(): String = BuildConfig.APPLICATION_ID

  @Provides @VersionName internal fun provideVersionName(): String = BuildConfig.VERSION_NAME

  @Provides @PreferenceInfo internal fun providePrefFileName(): String = AppConstants.PREF_NAME

  @Provides @Singleton internal fun providePrefHelper(appPreferenceHelper: AppPreferencesHelper):
      PreferencesHelper = appPreferenceHelper

  @Provides @Singleton internal fun provideProtectedApiHeader(
    @AppKey app_key: String,
    @PackageName packageName: String,
    @VersionName versionName: String
  ): ApiHeader.AuthApiHeader = ApiHeader.AuthApiHeader(
      _app_key = app_key,
      _package = packageName,
      _version = versionName
  )

  @Provides @Singleton internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiService =
    appApiHelper

  @Provides @Singleton internal fun provideOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong() // 5mb cache
    val myCache = Cache(ContextCompat.getCodeCacheDir(context), cacheSize)
    val builder = OkHttpClient().newBuilder()
    builder.cache(myCache)
//    if (BuildConfig.DEBUG) {
//      builder.addInterceptor(ChuckInterceptor(context))
//      builder.addNetworkInterceptor(CacheInterceptor())
//      builder.addNetworkInterceptor(StethoInterceptor())
//    }
    builder.connectTimeout(AppConstants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
    builder.readTimeout(AppConstants.TIMEOUT_READ, TimeUnit.SECONDS)
    builder.writeTimeout(AppConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
    return builder.build()
  }

//  @Provides @Singleton
//  internal fun provideRepoHelper(appDatabase: AppDatabase): DbHelper =
//    AppDbHelper(this)

  @Provides internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

  @Provides internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}