package com.sazib.ksl.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.data.db.DbHelper

class RegisterActivityVMF(private val dbHelper: DbHelper) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(RegisterActivityVM::class.java)) {
      return RegisterActivityVM(dbHelper) as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}