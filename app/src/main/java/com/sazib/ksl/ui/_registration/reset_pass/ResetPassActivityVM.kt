package com.sazib.ksl.ui._registration.reset_pass

import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DataHelper
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResetPassActivityVM(
  private val apiHelper: ApiService,
  private val dataHelper: DataHelper
) : BaseViewModel() {

  private val data = MutableLiveData<Resource<List<User>>>()

  suspend fun updateUserData(userData: User) {

    data.postValue(Resource.loading(null))

    mCompositeDisposable.add(
        dataHelper.checkUser(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              data.postValue(Resource.success(null))
            }, {})
    )
  }

  fun updateUserResponse(): MutableLiveData<Resource<List<User>>> = data
}
