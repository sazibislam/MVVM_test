package com.sazib.ksl.di.component

import android.app.Application
import com.sazib.ksl.data.service.App
import com.sazib.ksl.di.module.AppModule
import com.sazib.ksl.ui._registration.signin.SigninActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (AppModule::class)]
)
interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance fun application(application: Application): Builder
    fun build(): AppComponent
  }

  fun inject(app: App)

  /*activity*/
  fun inject(splashActivity: SigninActivity)
}