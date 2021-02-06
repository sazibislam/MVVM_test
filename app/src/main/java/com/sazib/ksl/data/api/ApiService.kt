package com.sazib.ksl.data.api

import com.sazib.ksl.data.api.response.SigninResponse
import io.reactivex.Observable

interface ApiService {

  fun getApiHeader(): ApiHeader

  fun login(data: Map<String, String>): Observable<SigninResponse>

//    fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest?): Single<LoginResponse?>?
//
//    fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest?): Single<LoginResponse?>?
//
//    fun doLogoutApiCall(): Single<LogoutResponse?>?
//
//    fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest?): Single<LoginResponse?>?

}