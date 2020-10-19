package com.sazib.ksl.ui.view_customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_view_customer.noItems
import kotlinx.android.synthetic.main.activity_view_customer.rvUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ViewCustomerActivity : BaseActivity(), CoroutineScope {

  private lateinit var vm: ViewCustomerActivityVM
  lateinit var layoutManager: LinearLayoutManager
  lateinit var customerAdapter: CustomerAdapter

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    const val TAG: String = "view_customer_activity"
    fun getStartIntent(context: Context): Intent = Intent(context, ViewCustomerActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_customer)

    initView()
  }

  private fun initView() {

    customerAdapter = CustomerAdapter()
    layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    rvUser.apply {
      layoutManager = this@ViewCustomerActivity.layoutManager
      itemAnimator = DefaultItemAnimator()
      adapter = this@ViewCustomerActivity.customerAdapter
    }

    vm =
      ViewModelProvider(
          this@ViewCustomerActivity,
          ViewCustomerActivityVMF(AppDataManager.getInstance().appDbHelper)
      )
          .get(ViewCustomerActivityVM::class.java)
          .also { vm_ ->

            vm_.getUserData()
                .observe(this, Observer {
                  when (it.status) {
                    SUCCESS ->
                      it.data?.let { data_ ->
                        noItems.visibility = View.GONE
                        customerAdapter.addDataToList(data_)
                      }
                    LOADING -> {
                      Log.d("data_category", "Progress")
                    }
                    ERROR -> {
                      Log.d("data_category", "error")
                    }
                  }
                })
          }

    launch {
      vm.fetchData()
    }
  }
}
