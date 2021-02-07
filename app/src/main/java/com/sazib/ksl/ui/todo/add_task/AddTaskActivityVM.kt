package com.sazib.ksl.ui.todo.add_task

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddTaskActivityVM(
  private val apiHelper: ApiService,
  private val dbHelper: DbHelper
) : BaseViewModel() {

  private val taskList = MutableLiveData<Resource<List<Task>>>()

  suspend fun saveTaskData(data: String) {

    taskList.postValue(Resource.loading(null))

    val listData = ArrayList<Task>()
    listData.add(Task(null, data, "7.30", "8.30", "", "", "", "test"))

    mCompositeDisposable.add(
        dbHelper.insertTaskList(listData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _ ->
              taskList.postValue(Resource.success(null))
              Log.d("user data", "checking")
            }, { taskList.postValue(Resource.error("Something Went Wrong", null, null)) })
    )
  }

  fun getTaskDataResponse(): MutableLiveData<Resource<List<Task>>> = taskList
}
