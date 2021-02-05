package com.sazib.ksl.ui.main

import android.util.Log
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityVM(private val dbHelper: DbHelper) : BaseViewModel() {

  suspend fun updateItemData(postalDetails: List<PostalDetails>) {

    mCompositeDisposable.add(
        dbHelper.insertPostalDetails(postalDetails)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              Log.d("postal data", "inserted")
            }, {})
    )
  }
}