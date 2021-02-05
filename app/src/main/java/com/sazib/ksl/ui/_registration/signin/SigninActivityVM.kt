package com.sazib.ksl.ui._registration.signin

import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.ui.base.BaseViewModel

class SigninActivityVM(private val apiHelper: ApiService) : BaseViewModel() {

//  private val signup = MutableLiveData<Resource<>>()
//
//  fun signup(data: Map<String, String>) {
//
//    signup.postValue(Resource.loading(null))
//
//    mCompositeDisposable.add(
//        apiHelper.signup(data)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ data_ ->
//              signup.postValue(Resource.success(data_))
//            }, { signup.postValue(Resource.error("Something Went Wrong", null, null)) })
//    )
//  }
//
//  fun getSigninResponseData(): MutableLiveData<Resource<>> = signup
}
