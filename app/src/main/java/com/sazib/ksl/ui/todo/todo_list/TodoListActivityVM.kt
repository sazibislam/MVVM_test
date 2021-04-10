package com.sazib.ksl.ui.todo.todo_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DbHelper
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodoListActivityVM(
    private val apiHelper: ApiService,
    private val dbHelper: DbHelper
) : BaseViewModel() {

    private val taskList = MutableLiveData<Resource<List<Task>>>()

    suspend fun fetchTaskData() {

        taskList.postValue(Resource.loading(null))

        mCompositeDisposable.add(
            dbHelper.loadTaskAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data_ ->
                    taskList.postValue(Resource.success(data_))
                    Log.d("user data", "checking")
                }, { taskList.postValue(Resource.error("Something Went Wrong", null, null)) })
        )
    }

    suspend fun removeTaskData(data: Task) {

        val listData = ArrayList<Task>()
        listData.add(data)

        taskList.postValue(Resource.loading(null))
        mCompositeDisposable.add(
            dbHelper.deleteTaskList(listData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data_ ->
                    taskList.postValue(Resource.success(data_))
                }, { taskList.postValue(Resource.error("Something Went Wrong", null, null)) })
        )
    }

    fun getTaskDataResponse(): MutableLiveData<Resource<List<Task>>> = taskList
}
