package com.sazib.ksl.ui.view_customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db.user.UserDetails
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewCustomerActivityVM(private val dbHelper: DbHelper) : BaseViewModel() {

  private val userListData = MutableLiveData<Resource<List<UserDetails>>>()

  /**
   * return last 10 user data
   * */
  suspend fun fetchData() {

    mCompositeDisposable.add(
        dbHelper.loadUserAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userListData_ ->
              userListData.postValue(Resource.success(userListData_))
            }, { userListData.postValue(Resource.error("Something Went Wrong", null)) })
    )
  }

  fun getUserData(): LiveData<Resource<List<UserDetails>>> = userListData
}