package com.sazib.ksl.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.sazib.ksl.data.api.response.SigninResponse
import io.reactivex.Observable
import javax.inject.Inject

class AppApiHelper @Inject constructor(private val mApiHeader: ApiHeader) : ApiService {

    override fun getApiHeader(): ApiHeader = mApiHeader

    override fun login(data: Map<String, String>): Observable<SigninResponse> =
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN)
            .addHeaders(mApiHeader.authApiHeader)
            .addBodyParameter(data)
            .build()
            .getObjectObservable(SigninResponse::class.java)

}