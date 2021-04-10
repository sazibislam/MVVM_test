package com.sazib.ksl.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.data.db.DbHelper

class MainActivityVMF(private val dbHelper: DbHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            return MainActivityVM(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}