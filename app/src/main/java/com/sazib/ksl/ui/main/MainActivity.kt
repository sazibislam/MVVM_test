package com.sazib.ksl.ui.main

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.ui._registration.signup.RegisterActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.main.ItemSearchDialogFragment.Companion
import com.sazib.ksl.ui.view_customer.ViewCustomerActivity
import com.sazib.ksl.utils.DataUtils.getPostalDetails
import kotlinx.android.synthetic.main.activity_main.btnRegister
import kotlinx.android.synthetic.main.activity_main.btnViewCustomer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: MainActivityVM
  private val TAG = "main_activity"

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initView()
  }

  private fun initView() {

    btnRegister.setOnClickListener(this@MainActivity)
    btnViewCustomer.setOnClickListener(this@MainActivity)

    vm =
      ViewModelProvider(
        this@MainActivity, MainActivityVMF(AppDataManager.getInstance().appDbHelper)
      )[MainActivityVM::class.java]

    launch {
      vm.updateItemData(getPostalDetails())
    }
  }

  override fun onClick(v: View?) {

    when (v?.id) {
      R.id.btnRegister -> startActivity(RegisterActivity.getStartIntent(this@MainActivity, TAG))
      R.id.btnViewCustomer -> {

        val itemSearchDialogFragment = ItemSearchDialogFragment.getStartIntent()
        itemSearchDialogFragment.isCancelable = true
        itemSearchDialogFragment.show(supportFragmentManager, itemSearchDialogFragment.tag)
        // startActivity(ViewCustomerActivity.getStartIntent(this@MainActivity))
      }
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}
