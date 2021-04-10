package com.sazib.ksl.ui.loan_calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.data.db.loan.Loan
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CalculatorActivityVM(
  private val apiHelper: ApiService,
  private val dbHelper: DbHelper
) : BaseViewModel() {

  private val loanList = MutableLiveData<Resource<List<Task>>>()

  suspend fun saveLoanData(
    amount: String,
    rateOfInterest: String,
    numberInstallment: String
  ) {

    loanList.postValue(Resource.loading(null))

    val AMOUNT = amount.toDoubleOrNull()
    val RATE = rateOfInterest.toDoubleOrNull()
    val INSTALLMENT = numberInstallment.toDoubleOrNull()

    val listData = ArrayList<Loan>()
    listData.add(Loan(null, AMOUNT, RATE, INSTALLMENT, null, null))

    mCompositeDisposable.add(
        dbHelper.insertLoanList(listData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              loanList.postValue(Resource.success(null))
              Log.d("user data", "checking")
            }, { loanList.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getTaskDataResponse(): MutableLiveData<Resource<List<Task>>> = loanList
}
