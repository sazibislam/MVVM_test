package com.sazib.ksl.ui._registration.walkthrough

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.sazib.ksl.R
import com.sazib.ksl.ui.base.BaseActivity

class WalkThroughActivity : BaseActivity(), OnClickListener {

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
  }

  override fun onClick(view: View?) {

  }
}