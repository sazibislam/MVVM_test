package com.sazib.ksl.ui.todo.edit_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.util.Supplier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.service.App
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class EditTaskActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  private lateinit var vm: EditActivityVM
  @Inject lateinit var apiHelper: ApiService

  companion object {
    private const val TAG = "edit_task_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, EditTaskActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_task)

    initView()
  }

  private fun initView() {

    App.appComponent.inject(this@EditTaskActivity)
    val supplier = Supplier { EditActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(EditActivityVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<EditActivityVM>(EditActivityVM::class.java)

    launch { vm.getTaskData() }

    vm.getTaskDataResponse()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              showMsg("Success")

            }
            LOADING -> {
            }
            ERROR -> showMsg("Something Wrong")
          }
        })
  }

  override fun onClick(view: View?) {

  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}