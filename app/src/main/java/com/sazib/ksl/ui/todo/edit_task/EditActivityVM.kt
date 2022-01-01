package com.sazib.ksl.ui.todo.edit_task

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.DataHelper
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.ui.base.BaseViewModel
import com.sazib.ksl.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditActivityVM(
  private val apiHelper: ApiService,
  private val dataHelper: DataHelper
) : BaseViewModel() {

  private val taskList = MutableLiveData<Resource<List<Task>>>()

  suspend fun getTaskData() {

    taskList.postValue(Resource.loading(null))

    mCompositeDisposable.add(
        dataHelper.loadTaskAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data_ ->
              taskList.postValue(Resource.success(data_))
              Log.d("user data", "checking")
            }, {})
    )
  }

  fun getTaskDataResponse(): MutableLiveData<Resource<List<Task>>> = taskList
}
