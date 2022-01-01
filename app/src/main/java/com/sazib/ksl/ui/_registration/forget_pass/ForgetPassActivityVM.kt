package com.sazib.ksl.ui._registration.forget_pass

import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DataHelper
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForgetPassActivityVM(
  private val apiHelper: ApiService,
  private val dataHelper: DataHelper
) : BaseViewModel() {

  private val user = MutableLiveData<Resource<List<User>>>()

  suspend fun checkUsername(username: String) {

    user.postValue(Resource.loading(null))

    val data = User(null, username, "", "", false)

    mCompositeDisposable.add(
        dataHelper.checkUserExist(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userData_ ->

                when (userData_.size) {
                    0 -> user.postValue(Resource.error("Wrong username", null, null))
                    else -> user.postValue(Resource.success(userData_))
                }
            }, { user.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getTaskDataResponse(): MutableLiveData<Resource<List<User>>> = user
}
