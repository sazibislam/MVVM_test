package com.sazib.ksl.ui.todo.add_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.util.Supplier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.service.App
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.ui.todo.add_task.adapter.AddTaskAdapter
import com.sazib.ksl.ui.todo.todo_list.TodoListActivity
import com.sazib.ksl.utils.DataUtils
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_add_task.btnAddTask
import kotlinx.android.synthetic.main.activity_add_task.etAddTask
import kotlinx.android.synthetic.main.activity_add_task.rvTaskType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddTaskActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: AddTaskActivityVM
  @Inject lateinit var apiHelper: ApiService
  lateinit var layoutManager: GridLayoutManager
  lateinit var addAdapter: AddTaskAdapter

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    private const val TAG = "add_task_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, AddTaskActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_task)

    initView()
  }

  private fun initView() {

    App.appComponent.inject(this@AddTaskActivity)

    val supplier =
      Supplier { AddTaskActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(AddTaskActivityVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<AddTaskActivityVM>(AddTaskActivityVM::class.java)

    addAdapter = AddTaskAdapter()
    layoutManager = GridLayoutManager(this@AddTaskActivity, 3)

    rvTaskType.apply {
      layoutManager = this@AddTaskActivity.layoutManager
      itemAnimator = DefaultItemAnimator()
      adapter = this@AddTaskActivity.addAdapter
      addAdapter.addDataToList(DataUtils.getTaskType())
    }

    vm.getTaskDataResponse()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              showMsg("success")
              startActivity(TodoListActivity.getStartIntent(this@AddTaskActivity, TAG))
              finishIt()
            }
            LOADING -> {
            }
            ERROR -> {
              //show error dialog
            }
          }
        })

    btnAddTask.setOnClickListener(this@AddTaskActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      R.id.btnAddTask -> {
        if (!etAddTask.text.isNullOrBlank())
          launch { vm.saveTaskData("${etAddTask.text}") }
      }
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}