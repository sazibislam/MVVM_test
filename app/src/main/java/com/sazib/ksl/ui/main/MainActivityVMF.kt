package com.sazib.ksl.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.data.db.DataHelper

class MainActivityVMF(private val dataHelper: DataHelper) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
      return MainActivityVM(dataHelper) as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}