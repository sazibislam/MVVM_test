package com.sazib.ksl.ui.view_customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewCustomerActivityVM(private val dbHelper: DbHelper) : BaseViewModel() {

  private val userListData = MutableLiveData<Resource<List<PostalDetails>>>()

  /**
   * return last 10 user data
   * */
  suspend fun fetchData() {

    mCompositeDisposable.add(
        dbHelper.loadPostalDetailsAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userListData_ ->
              userListData.postValue(Resource.success(userListData_))
            }, { userListData.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getUserData(): LiveData<Resource<List<PostalDetails>>> = userListData
}