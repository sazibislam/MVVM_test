package com.sazib.ksl.ui._registration.signin

import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SigninActivityVM(
    private val apiHelper: ApiService,
    private val dbHelper: DbHelper
) : BaseViewModel() {

    private val isLogin = MutableLiveData<Resource<List<User>>>()

    suspend fun requestLogin(
        username: String,
        password: String
    ) {

        isLogin.postValue(Resource.loading(null))

        val data = User(null, username, "", password, false)

        mCompositeDisposable.add(
            dbHelper.checkUser(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data_ ->
                    when (data_.size) {
                        0 -> isLogin.postValue(
                            Resource.error(
                                "password mismatched or user not found",
                                null,
                                null
                            )
                        )
                        else -> isLogin.postValue(Resource.success(data_))
                    }
                }, {})
        )
    }

    fun getSigninResponseData(): MutableLiveData<Resource<List<User>>> = isLogin

    /**
     * for network calling.
     * */
/*  private val signup = MutableLiveData<Resource<SigninResponse>>()

  fun requestLogin(data: Map<String, String>) {

    signup.postValue(Resource.loading(null))

    mCompositeDisposable.add(
        apiHelper.login(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data_ ->
              signup.postValue(Resource.success(data_))
            }, { signup.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getSigninResponseData(): MutableLiveData<Resource<SigninResponse>> = signup*/
}
