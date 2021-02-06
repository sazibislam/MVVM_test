package com.sazib.ksl.di.component

import android.app.Application
import com.sazib.ksl.data.service.App
import com.sazib.ksl.di.module.AppModule
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.todo.edit_task.EditTaskActivity
import com.sazib.ksl.ui.todo.todo_list.TodoListActivity
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

  fun inject(signinActivity: SigninActivity)
  fun inject(todoListActivity: TodoListActivity)
  fun inject(editTaskActivity: EditTaskActivity)
}