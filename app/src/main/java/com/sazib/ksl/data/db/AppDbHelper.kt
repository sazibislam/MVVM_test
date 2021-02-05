package com.sazib.ksl.data.db

import android.content.Context
import com.sazib.ksl.data.db.user_details.UserDetails
import com.sazib.ksl.data.db.post_code.PostalDetails
import io.reactivex.Observable

//repository class
open class AppDbHelper(context: Context) : DbHelper {

  private val mAppDatabase: AppDatabase = AppDatabase.getInstance(context)

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