package com.sazib.ksl.ui._registration.successfull

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.sazib.ksl.R
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_successful.tvLogin

class SuccessfulActivity : BaseActivity(), OnClickListener {

  companion object {
    private const val TAG = "successful_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, SuccessfulActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_successful)

    initView()
  }

  private fun initView() {
    tvLogin.setOnClickListener(this@SuccessfulActivity)
  }

  override fun onClick(view: View?) {

    when (view?.id) {
      R.id.tvLogin -> startActivity(SigninActivity.getStartIntent(this@SuccessfulActivity, TAG))
    }
  }
}