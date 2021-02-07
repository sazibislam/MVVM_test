package com.sazib.ksl.ui._registration.reset_pass

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
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui._registration.successfull.SuccessfulActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_reset_pass.inputConfirmPassword
import kotlinx.android.synthetic.main.activity_reset_pass.inputPassword
import kotlinx.android.synthetic.main.activity_reset_pass.tvConfirm
import kotlinx.android.synthetic.main.activity_reset_pass.tvLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ResetPassActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: ResetPassActivityVM
  @Inject lateinit var apiHelper: ApiService

  private var userData: User? = null

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    private val TAG = "signin_activity"
    private const val USER_DATA = "user_data"
    fun getStartIntent(
      context: Context,
      userData: ArrayList<User>,
      tag: String
    ): Intent = Intent(context, ResetPassActivity::class.java).apply {
      putExtra(TAG, tag)
      putExtra(USER_DATA, userData)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reset_pass)

    initView()
  }

  private fun initView() {

    App.appComponent.inject(this@ResetPassActivity)

    val supplier =
      Supplier { ResetPassActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(ResetPassActivityVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<ResetPassActivityVM>(ResetPassActivityVM::class.java)

    val data = intent?.getSerializableExtra(USER_DATA) as ArrayList<User>
    userData = data[0]

    vm.updateUserResponse()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              showMsg("success")
              startActivity(SuccessfulActivity.getStartIntent(this@ResetPassActivity, TAG))
              finishIt()
            }
            LOADING -> {
            }
            ERROR -> showMsg("Error")
          }
        })

    tvConfirm.setOnClickListener(this@ResetPassActivity)
    tvLogin.setOnClickListener(this@ResetPassActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      R.id.tvLogin -> startActivity(SigninActivity.getStartIntent(this@ResetPassActivity, TAG))
      R.id.tvConfirm -> {
        if (inputPassword.text == inputConfirmPassword.text && !inputConfirmPassword.text.isNullOrBlank())

          userData?.passwordhash = "${inputConfirmPassword.text}"

        launch { userData?.let { vm.updateUserData(it) } }
      }
    }
  }
}