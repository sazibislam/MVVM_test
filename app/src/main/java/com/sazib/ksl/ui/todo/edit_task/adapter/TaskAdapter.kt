package com.sazib.ksl.ui.todo.edit_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.db._task.Task
import com.sazib.ksl.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_task_list.view.tvTaskBody
import kotlinx.android.synthetic.main.list_item_task_list.view.tvTaskTitle

class TaskAdapter(private val data: MutableList<Task> = ArrayList()) :
    RecyclerView.Adapter<BaseViewHolder>() {

  override fun getItemCount() = data.size

  override fun onBindViewHolder(
    holder: BaseViewHolder,
    position: Int
  ) = holder.onBind(position)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = ViewHolder(
      LayoutInflater.from(parent.context)
          .inflate(R.layout.list_item_task_list, parent, false)
  )

  internal fun addDataToList(data: List<Task>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(view: View) : BaseViewHolder(view) {

    override fun clear() {
      itemView.tvTaskTitle.text = ""
      itemView.tvTaskBody.text = ""
    }

    override fun onBind(position: Int) {

      val model = data[position]
      itemView.tvTaskTitle.text = model.tasktitle
      itemView.tvTaskBody.text = model.description
    }
  }
}

