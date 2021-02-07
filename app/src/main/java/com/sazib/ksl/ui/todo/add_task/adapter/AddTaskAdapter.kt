package com.sazib.ksl.ui.todo.add_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.ui.base.BaseViewHolder
import com.sazib.ksl.ui.todo.edit_task.model.TaskTypeModel
import kotlinx.android.synthetic.main.row_view_add_task.view.txtSubmit

class AddTaskAdapter(private val data: MutableList<TaskTypeModel> = ArrayList()) :
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
          .inflate(R.layout.row_view_add_task, parent, false)
  )

  internal fun addDataToList(data: List<TaskTypeModel>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(view: View) : BaseViewHolder(view) {

    override fun clear() {
      itemView.txtSubmit.text = ""
    }

    override fun onBind(position: Int) {

      val model = data[position]
      itemView.txtSubmit.text = model.title
//      itemView.btnAddTask.setBackgroundColor(Color.parseColor(getColorCode(position)))
    }
  }
}

