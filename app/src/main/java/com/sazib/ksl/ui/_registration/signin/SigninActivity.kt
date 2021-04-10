package com.sazib.ksl.ui._registration.signin

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
import com.sazib.ksl.ui._registration.forget_pass.ForgetPassActivity
import com.sazib.ksl.ui._registration.signup.RegisterActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.ui.todo.edit_task.EditTaskActivity
import com.sazib.ksl.utils.Status.*
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SigninActivity : BaseActivity(), OnClickListener, CoroutineScope {

    private lateinit var vm: SigninActivityVM

    @Inject
    lateinit var apiHelper: ApiService

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        private const val TAG = "signin_activity"
        fun getStartIntent(
            context: Context,
            tag: String
        ): Intent = Intent(context, SigninActivity::class.java).apply {
            putExtra(TAG, tag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        initView()
    }

    private fun initView() {

        App.appComponent.inject(this@SigninActivity)

        val supplier =
            Supplier { SigninActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
        val factory = ViewModelProviderFactory(SigninActivityVM::class.java, supplier)
        vm =
            ViewModelProvider(this, factory).get<SigninActivityVM>(SigninActivityVM::class.java)

        tvLogin.setOnClickListener(this@SigninActivity)
        tvSignUp.setOnClickListener(this@SigninActivity)
        tvForgetPass.setOnClickListener(this@SigninActivity)

        vm.getSigninResponseData()
            .observe(this, Observer {
                when (it.status) {
                    SUCCESS -> {
                        showMsg("Success")
                        startActivity(EditTaskActivity.getStartIntent(this@SigninActivity, TAG))
                        finishIt()
                    }
                    LOADING -> {
                    }
                    ERROR -> it.message?.let { data_ -> showMsg(data_) }
                }
            })
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.tvLogin -> validation()
            R.id.tvSignUp -> startActivity(
                RegisterActivity.getStartIntent(
                    this@SigninActivity,
                    TAG
                )
            )
            R.id.tvForgetPass -> startActivity(
                ForgetPassActivity.getStartIntent(this@SigninActivity, TAG)
            )
        }
    }

    private fun validation() {

        val message = "Validation failed"
        val isValid = true

        when (isValid) {
            true ->
                if (isNetworkConnected(this@SigninActivity))
                    launch {
                        vm.requestLogin("${inputUserName.text}", "${inputPassword.text}")
                    }
                else showMsg("No Internet")
            else -> showMsg(message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    /*
    private fun signupData(): Map<String, String> {
      val data: MutableMap<String, String> = HashMap()
      data["username"] = "${inputUserName.text}"
      data["password"] = "${inputPassword.text}"
      return data
    }*/
}