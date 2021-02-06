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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.todo.todo_list.adapter.EditAdapter
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : BaseActivity(), OnClickListener, EditAdapter.Callback {

  private lateinit var vm: TodoListActivityVM
  lateinit var layoutManager: LinearLayoutManager
  lateinit var taskAdapter: EditAdapter
  private lateinit var colorDrawableBackground: ColorDrawable
  private lateinit var deleteIcon: Drawable

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

    taskAdapter = EditAdapter()
    layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    rvTask.apply {
      layoutManager = this@TodoListActivity.layoutManager
      itemAnimator = DefaultItemAnimator()
      adapter = this@TodoListActivity.taskAdapter.apply {
        setCallback(this@TodoListActivity)
      }
    }

//    vm =
//      ViewModelProvider(this, InventoryViewModelFactory(AppDataManager.getInstance().appDbHelper))
//        .get(TodoListActivityVM::class.java)
//        .also { data_ ->
//          data_.getData()
//            .observe(this, Observer {
//              when (it.status) {
//                Status.SUCCESS -> it.data?.let { data_ -> inventoryAdapter.addDataToList(data_) }
//                Status.LOADING -> {
//                  Log.d("data_category", "Progress")
//                }
//                Status.ERROR -> {
//                  Log.d("data_category", "error")
//                }
//              }
//            })
//        }
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
        val iconMarginVertical =
          (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

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