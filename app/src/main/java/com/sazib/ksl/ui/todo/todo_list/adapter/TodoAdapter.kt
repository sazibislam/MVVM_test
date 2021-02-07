package com.sazib.ksl.ui.todo.todo_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sazib.ksl.R
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.row_view_todo.view.tvBody
import kotlinx.android.synthetic.main.row_view_todo.view.tvTitle

class TodoAdapter(private val data: MutableList<Task> = ArrayList()) :
    RecyclerView.Adapter<BaseViewHolder>() {

  private var removedPosition: Int = 0
  private lateinit var removedItem: Task
  private var callback: Callback? = null

  override fun getItemCount() = data.size

  override fun onBindViewHolder(
    holder: BaseViewHolder,
    position: Int
  ) = holder.onBind(position)

  internal fun setCallback(callback: Callback) {
    this.callback = callback
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = ViewHolder(
      LayoutInflater.from(parent.context)
          .inflate(R.layout.row_view_todo, parent, false)
  )

  internal fun addDataToList(data: List<Task>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(view: View) : BaseViewHolder(view) {

    override fun clear() {
      itemView.tvTitle.text = ""
      itemView.tvBody.text = ""
    }

    override fun onBind(position: Int) {

      val model = data[position]
      itemView.tvTitle.text = model.tasktitle
      itemView.tvBody.text = model.description
    }
  }

  fun removeItem(
    position: Int,
    viewHolder: RecyclerView.ViewHolder
  ) {
    removedItem = data[position]
    removedPosition = position

    callback?.removeItem(removedItem)

    data.removeAt(position)
    notifyItemRemoved(position)

    Snackbar.make(viewHolder.itemView, "$removedItem removed", Snackbar.LENGTH_LONG)
        .show()
  }

  interface Callback {
    fun removeItem(data: Task)
  }
}

