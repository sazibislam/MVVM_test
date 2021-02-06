package com.sazib.ksl.ui._registration.walkthrough

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.DataUtils.getDummyUser
import kotlinx.android.synthetic.main.activity_walkthrough.tvGetStarted
import kotlinx.android.synthetic.main.activity_walkthrough.tvLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WalkThroughActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: WalkThroughVM

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    private const val TAG = "walkthrough_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, WalkThroughActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_walkthrough)

    initView()
  }

  private fun initView() {

    val supplier =
      Supplier { WalkThroughVM(AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(WalkThroughVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<WalkThroughVM>(WalkThroughVM::class.java)

    /*
    * inserting some dummy user in local db
    *  */
    launch {
      vm.insertDummyUser(getDummyUser())
    }

    tvLogin.setOnClickListener(this@WalkThroughActivity)
    tvGetStarted.setOnClickListener(this@WalkThroughActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      R.id.tvLogin, R.id.tvGetStarted -> startActivity(
          SigninActivity.getStartIntent(this@WalkThroughActivity, TAG)
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
  }
}