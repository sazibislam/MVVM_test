package com.sazib.ksl.ui._registration.walkthrough

import android.util.Log
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WalkThroughVM(private val dbHelper: DbHelper) : BaseViewModel() {

//  private val user = MutableLiveData<Resource<User>>()

  suspend fun insertDummyUser(data: List<User>) {

//    user.postValue(Resource.loading(null))

    mCompositeDisposable.add(
        dbHelper.insertUser(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
//              user.postValue(Resource.success(null))
              Log.d("user data", "checking")
            }, {})
    )
  }

//  fun insertDataResponse(): MutableLiveData<Resource<User>> = user
}
