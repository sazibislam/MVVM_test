package com.sazib.ksl.data.db

import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.data.db.user_details.UserDetails
import io.reactivex.Observable

interface DbHelper {

  suspend fun checkUser(user: User): Observable<List<User>>
  suspend fun insertUser(user: List<User>): Observable<Boolean>

  suspend fun insertUserDetails(userDetails: List<UserDetails>): Observable<Boolean>
  suspend fun loadUserAll(): Observable<List<UserDetails>>

  suspend fun insertPostalDetails(postalDetails: List<PostalDetails>): Observable<Boolean>
  suspend fun loadPostalDetailsAll(): Observable<List<PostalDetails>>
}