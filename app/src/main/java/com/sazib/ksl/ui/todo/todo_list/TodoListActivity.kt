package com.sazib.ksl.ui.todo.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.sazib.ksl.R
import com.sazib.ksl.ui.base.BaseActivity

class TodoListActivity : BaseActivity(), OnClickListener {

  companion object {
    private const val TAG = "todo_list_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, TodoListActivity::class.java).apply {
      putExtra(TAG, tag)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todo_list)

    initView()
  }

  private fun initView() {
  }

  override fun onClick(view: View?) {

  }
}