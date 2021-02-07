package com.sazib.ksl.ui._registration.forget_pass

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
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.data.service.App
import com.sazib.ksl.ui._registration.reset_pass.ResetPassActivity
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_forget_password.inputUserName
import kotlinx.android.synthetic.main.activity_forget_password.tvLogin
import kotlinx.android.synthetic.main.activity_forget_password.tvRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForgetPassActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: ForgetPassActivityVM
  @Inject lateinit var apiHelper: ApiService

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    private const val TAG = "successful_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, ForgetPassActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_forget_password)

    initView()
  }

  private fun initView() {

    App.appComponent.inject(this@ForgetPassActivity)

    val supplier =
      Supplier { ForgetPassActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(ForgetPassActivityVM::class.java, supplier)
    vm =
      ViewModelProvider(this, factory).get<ForgetPassActivityVM>(ForgetPassActivityVM::class.java)

    vm.getTaskDataResponse()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              showMsg("success")
              startActivity(
                  ResetPassActivity.getStartIntent(
                      this@ForgetPassActivity, it.data!! as ArrayList<User>, TAG
                  )
              )
              finishIt()
            }
            LOADING -> {
            }
            ERROR -> it.message?.let { msg_ -> showMsg(msg_) }
          }
        })

    tvLogin.setOnClickListener(this@ForgetPassActivity)
    tvRequest.setOnClickListener(this@ForgetPassActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      R.id.tvRequest -> launch { vm.checkUsername("${inputUserName.text}") }
      R.id.tvLogin -> startActivity(SigninActivity.getStartIntent(this@ForgetPassActivity, TAG))
    }
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}