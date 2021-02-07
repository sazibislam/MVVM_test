package com.sazib.ksl.ui._registration.signup

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
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_register.btnSubmit
import kotlinx.android.synthetic.main.activity_register.inputEmail
import kotlinx.android.synthetic.main.activity_register.inputName
import kotlinx.android.synthetic.main.activity_register.inputPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: RegisterActivityVM

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    const val TAG: String = "register_up_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, RegisterActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    initView()
  }

  private fun initView() {

    btnSubmit.setOnClickListener(this@RegisterActivity)

    val supplier = Supplier { RegisterActivityVM(AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(RegisterActivityVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<RegisterActivityVM>(RegisterActivityVM::class.java)

    vm.getUserData()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              //show success dialog
              startActivity(SigninActivity.getStartIntent(this@RegisterActivity, TAG))
              finishIt()
            }
            LOADING -> {
            }
            ERROR -> {
              //show error dialog
            }
          }
        })
  }

  private fun validation() {

    var isValidationOk = true
    var message = "Validation Failed"

    if (inputPassword.text.isNullOrEmpty() && inputPassword.textSize < 6 && inputPassword.textSize > 11) {
      isValidationOk = false
      message = "password is between 6 to 11"
    }

    if (inputEmail.text.isNullOrEmpty()) {
      isValidationOk = false
      message = "Email is Empty"
    }

    if (inputName.text.isNullOrEmpty() && inputName.textSize > 4) {
      isValidationOk = false
      message = "Name Minimum 4 characters but digit not allowed"
    }

    when (isValidationOk) {
      true -> {
        val userData = User()
        userData.createdAt = System.currentTimeMillis()
        userData.updatedAt = System.currentTimeMillis()
        userData.id = null
        userData.username = inputName.text.toString()
        userData.passwordhash = inputPassword.text.toString()
        userData.email = inputEmail.text.toString()

        launch { vm.updateItemData(userData) }
      }
      else -> showAlert("KSL", message)
    }
  }

  override fun onClick(v: View?) {

    when (v?.id) {
      R.id.btnSubmit -> validation()
    }
  }
}
