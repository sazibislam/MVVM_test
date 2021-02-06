package com.sazib.ksl.ui._registration.reset_pass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.sazib.ksl.R
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.ui.base.BaseActivity
import javax.inject.Inject

class ResetPassActivity : BaseActivity(), OnClickListener {

  //  private lateinit var vm: SigninActivityVM
  @Inject lateinit var apiHelper: ApiService

  companion object {
    private val TAG = "signin_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, ResetPassActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reset_pass)

    initView()
  }

  private fun initView() {
  }

  override fun onClick(view: View?) {

  }
}