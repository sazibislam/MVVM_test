package com.sazib.ksl.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.data.db.user_details.UserDetails
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterActivityVM(private val dbHelper: DbHelper) : BaseViewModel() {

  private val userListData = MutableLiveData<Resource<List<UserDetails>>>()
  private val postalListData = MutableLiveData<Resource<List<PostalDetails>>>()

  suspend fun updateItemData(userData: UserDetails) {

    val listData = ArrayList<UserDetails>()
    listData.add(userData)

    userListData.postValue(Resource.loading(null))
    mCompositeDisposable.add(
        dbHelper.insertUserDetails(listData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              userListData.postValue(Resource.success(null))
            }, { userListData.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

//  private suspend fun fetchData() {
//
//    userListData.postValue(Resource.loading(null))
//    mCompositeDisposable.add(
//        dbHelper.loadUserAll()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ userList ->
//              userListData.postValue(Resource.success(userList))
//            }, { userListData.postValue(Resource.error("Something Went Wrong", null)) })
//    )
//  }

  suspend fun fetchPostalData() {

    postalListData.postValue(Resource.loading(null))
    mCompositeDisposable.add(
        dbHelper.loadPostalDetailsAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ postData_ ->
              postalListData.postValue(Resource.success(postData_))
            }, { postalListData.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getUserData(): LiveData<Resource<List<UserDetails>>> = userListData
  fun getPostalData(): LiveData<Resource<List<PostalDetails>>> = postalListData
}