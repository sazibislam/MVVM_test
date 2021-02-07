package com.sazib.ksl.data.db

import android.content.Context
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.data.db.user_details.UserDetails
import io.reactivex.Observable

open class AppDbHelper(context: Context) : DbHelper {

    private val mAppDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun checkUser(user: User): Observable<List<User>> = Observable.fromCallable {
        mAppDatabase.userDao()
            .loadUser(userName = user.username!!, pass = user.passwordhash!!)
    }

    override suspend fun checkUserExist(user: User): Observable<List<User>> =
        Observable.fromCallable {
            mAppDatabase.userDao()
                .loadUser(userName = user.username!!)
        }

    override suspend fun insertUser(user: List<User>): Observable<Boolean> {
        mAppDatabase.userDao()
            .insertAll(user)
        return Observable.just(user.count() > 0)
    }

    override suspend fun loadTaskAll(): Observable<List<Task>> = Observable.fromCallable {
        mAppDatabase.taskDao()
            .loadAll()
  }

  override suspend fun insertTaskList(task: List<Task>): Observable<Boolean> {
    mAppDatabase.taskDao()
        .insertAll(task)
    return Observable.just(task.count() > 0)
  }

  override suspend fun deleteTaskList(task: List<Task>): Observable<List<Task>> =
    Observable.fromCallable {
      mAppDatabase.taskDao()
          .deleteTask(task)
      mAppDatabase.taskDao()
          .loadAll()
    }

  override suspend fun insertUserDetails(userDetails: List<UserDetails>): Observable<Boolean> {
    mAppDatabase.userDetailsDao()
        .insertAll(userDetails)
    return Observable.just(userDetails.count() > 0)
  }

  override suspend fun loadUserAll(): Observable<List<UserDetails>> = Observable.fromCallable {
    mAppDatabase.userDetailsDao()
        .loadAll()
  }

  override suspend fun insertPostalDetails(postalDetails: List<PostalDetails>): Observable<Boolean> {
    mAppDatabase.postalDetailsDao()
        .deleteAll()
    mAppDatabase.postalDetailsDao()
        .insertAll(postalDetails)
    return Observable.just(postalDetails.count() > 0)
  }

  override suspend fun loadPostalDetailsAll(): Observable<List<PostalDetails>> =
    Observable.fromCallable {
      mAppDatabase.postalDetailsDao()
          .loadAll()
    }
}