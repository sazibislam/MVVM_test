package com.sazib.ksl.ui.todo.todo_list

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.api.ApiService
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.data.pref.PreferencesHelper
import com.sazib.ksl.data.service.App
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.ui.todo.todo_list.adapter.EditAdapter
import kotlinx.android.synthetic.main.activity_todo_list.*
import javax.inject.Inject

class TodoListActivity : BaseActivity(), OnClickListener, EditAdapter.Callback {

  private lateinit var vm: TodoListActivityVM
  lateinit var layoutManager: LinearLayoutManager
  lateinit var taskAdapter: EditAdapter
  private lateinit var colorDrawableBackground: ColorDrawable
  private lateinit var deleteIcon: Drawable
  @Inject lateinit var apiHelper: ApiService
  @Inject lateinit var pref: PreferencesHelper

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
    initListener()
  }

  private fun initView() {

    App.appComponent.inject(this@TodoListActivity)

    taskAdapter = EditAdapter()
    layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    rvTask.apply {
      layoutManager = this@TodoListActivity.layoutManager
      itemAnimator = DefaultItemAnimator()
      adapter = this@TodoListActivity.taskAdapter.apply {
        setCallback(this@TodoListActivity)
      }
    }

    val supplier =
      Supplier { TodoListActivityVM(apiHelper, AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(TodoListActivityVM::class.java, supplier)
    vm =
      ViewModelProvider(this, factory).get<TodoListActivityVM>(TodoListActivityVM::class.java)
  }

  private fun initListener() {

    colorDrawableBackground = ColorDrawable(ContextCompat.getColor(this, R.color.colorRed))
    deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_trash)!!

    val itemTouchHelperCallback = object :
      ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder2: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        swipeDirection: Int
      ) {
        taskAdapter.removeItem(viewHolder.bindingAdapterPosition, viewHolder)
      }

      override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
      ) {
        val itemView = viewHolder.itemView
        val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

        if (dX > 0) {
          colorDrawableBackground.setBounds(
            itemView.left, itemView.top, dX.toInt(), itemView.bottom
          )
          deleteIcon.setBounds(
            itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
            itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
            itemView.bottom - iconMarginVertical
          )
        } else {
          colorDrawableBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
          )
          deleteIcon.setBounds(
            itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
            itemView.top + iconMarginVertical,
            itemView.right - iconMarginVertical,
            itemView.bottom - iconMarginVertical
          )
          deleteIcon.level = 0
        }

        colorDrawableBackground.draw(c)
        c.save()
        if (dX > 0)
          c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
        else
          c.clipRect(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
          )

        deleteIcon.draw(c)
        c.restore()

        super.onChildDraw(
          c,
          recyclerView,
          viewHolder,
          dX,
          dY,
          actionState,
          isCurrentlyActive
        )
      }
    }

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(rvTask)
  }

  override fun onClick(view: View?) {

  }

  override fun removeItem(data: MutableList<PostalDetails>) {

  }
}