package com.sazib.ksl.ui.todo.edit_task

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.service.App
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.ui.todo.add_task.AddTaskActivity
import com.sazib.ksl.ui.todo.edit_task.adapter.EditAdapter
import com.sazib.ksl.ui.todo.edit_task.adapter.TaskAdapter
import com.sazib.ksl.utils.DataUtils
import com.sazib.ksl.utils.Status.*
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class EditTaskActivity : BaseActivity(), OnClickListener, CoroutineScope {

    private lateinit var vm: EditActivityVM

    @Inject
    lateinit var apiHelper: ApiService

    lateinit var taskEditAdapter: EditAdapter
    lateinit var taskListAdapter: TaskAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var taskLayout: LinearLayoutManager

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

        taskEditAdapter = EditAdapter()
        layoutManager = GridLayoutManager(this@EditTaskActivity, 2)

        App.appComponent.inject(this@EditTaskActivity)
        val supplier =
            Supplier { EditActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
        val factory = ViewModelProviderFactory(EditActivityVM::class.java, supplier)
        vm = ViewModelProvider(this, factory).get<EditActivityVM>(EditActivityVM::class.java)

        rvType.apply {
            layoutManager = this@EditTaskActivity.layoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = this@EditTaskActivity.taskEditAdapter
            taskEditAdapter.addDataToList(DataUtils.getTaskType())
        }

        taskListAdapter = TaskAdapter()
        taskLayout = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

        rvTasks.apply {
            layoutManager = this@EditTaskActivity.taskLayout
            itemAnimator = DefaultItemAnimator()
            adapter = this@EditTaskActivity.taskListAdapter
            taskListAdapter.addDataToList(DataUtils.getTaskList())
        }

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

        ivEditPlus.setOnClickListener(this@EditTaskActivity)
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.ivEditPlus -> startActivity(
                AddTaskActivity.getStartIntent(
                    this@EditTaskActivity,
                    TAG
                )
            )
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}