package com.sazib.ksl.data.db

import com.sazib.ksl.data.db.user.UserDetails
import com.sazib.ksl.data.post_code.PostalDetails
import io.reactivex.Observable

interface DbHelper {

  suspend fun insertUserDetails(userDetails: List<UserDetails>): Observable<Boolean>
  suspend fun loadUserAll(): Observable<List<UserDetails>>

  suspend fun insertPostalDetails(postalDetails: List<PostalDetails>): Observable<Boolean>
  suspend fun loadPostalDetailsAll(): Observable<List<PostalDetails>>
}