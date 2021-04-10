package com.sazib.ksl.ui.loan_calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.util.Supplier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.service.App
import com.sazib.ksl.databinding.ActivityCalculatorBinding
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_calculator.etAddAmount
import kotlinx.android.synthetic.main.activity_calculator.etAddRateOfInterest
import kotlinx.android.synthetic.main.activity_calculator.etNumberInstallment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CalculatorActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: CalculatorActivityVM
  @Inject lateinit var apiHelper: ApiService
  private lateinit var binding: ActivityCalculatorBinding
  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var loanAdapter: LoanAdapter

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    private const val TAG = "add_calculator"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, CalculatorActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityCalculatorBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initView()
  }

  private fun initView() {

    App.appComponent.inject(this@CalculatorActivity)

    val supplier =
      Supplier { CalculatorActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(CalculatorActivityVM::class.java, supplier)
    vm =
      ViewModelProvider(this, factory).get<CalculatorActivityVM>(CalculatorActivityVM::class.java)


    loanAdapter = LoanAdapter()
    layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    binding.rvLoan.apply {
      layoutManager = this@CalculatorActivity.layoutManager
      itemAnimator = DefaultItemAnimator()
      adapter = this@CalculatorActivity.loanAdapter
    }

    vm.getTaskDataResponse()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              showMsg("success")
              it.data?.let { data_ ->
                loanAdapter.addDataToList(data_)
              }
            }
            LOADING -> {
            }
            ERROR -> {
              //show error dialog
            }
          }
        })

    launch { vm.getData() }

    binding.btnAddTask.setOnClickListener(this@CalculatorActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      binding.btnAddTask.id -> {
        launch {
          vm.saveLoanData(
              "${etAddAmount.text}", "${etAddRateOfInterest.text}", "${etNumberInstallment.text}"
          )
        }
      }
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}