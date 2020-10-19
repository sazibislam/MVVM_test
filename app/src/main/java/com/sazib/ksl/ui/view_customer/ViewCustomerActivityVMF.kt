package com.sazib.ksl.ui.view_customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.data.db.DbHelper

class ViewCustomerActivityVMF(private val dbHelper: DbHelper) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ViewCustomerActivityVM::class.java)) {
      return ViewCustomerActivityVM(dbHelper) as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}