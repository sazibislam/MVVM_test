package com.sazib.ksl.ui.todo.todo_list

import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.ui.base.BaseViewModel

class TodoListActivityVM(
  private val apiHelper: ApiService,
  private val dbHelper: DbHelper
) : BaseViewModel() {

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
