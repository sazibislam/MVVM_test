package com.sazib.ksl.ui._registration.forget_pass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.sazib.ksl.R
import com.sazib.ksl.ui.base.BaseActivity

class ForgetPassActivity : BaseActivity(), OnClickListener {

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
  }

  override fun onClick(view: View?) {

  }
}