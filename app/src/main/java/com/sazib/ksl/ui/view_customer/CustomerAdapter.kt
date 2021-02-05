package com.sazib.ksl.ui.view_customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.R
import com.sazib.ksl.data.db.user_details.UserDetails
import com.sazib.ksl.utils.BaseViewHolder
import kotlinx.android.synthetic.main.row_view_user.view.postName
import kotlinx.android.synthetic.main.row_view_user.view.postPhone

class CustomerAdapter(private val data: MutableList<UserDetails> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder>() {

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
          .inflate(R.layout.row_view_user, parent, false)
  )

  internal fun addDataToList(data: List<UserDetails>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(view: View) : BaseViewHolder(view) {

    override fun clear() {
      itemView.postName.text = ""
      itemView.postPhone.text = ""
    }

    override fun onBind(position: Int) {

      val model = data[position]
      itemView.postName.text = model.name
      itemView.postPhone.text = model.phone
    }
  }
}

