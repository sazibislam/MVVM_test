package com.sazib.ksl.ui._registration.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.db.DataHelper
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterActivityVM(private val dataHelper: DataHelper) : BaseViewModel() {

  private val userListData = MutableLiveData<Resource<List<User>>>()

  suspend fun updateItemData(userData: User) {

    val listData = ArrayList<User>()
    listData.add(userData)

    userListData.postValue(Resource.loading(null))
    mCompositeDisposable.add(
        dataHelper.insertUser(listData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              userListData.postValue(Resource.success(null))
            }, { userListData.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getUserData(): LiveData<Resource<List<User>>> = userListData
}