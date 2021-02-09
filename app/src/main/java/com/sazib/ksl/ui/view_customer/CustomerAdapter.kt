package com.sazib.ksl.ui.view_customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.databinding.RowViewUserBinding
import com.sazib.ksl.ui.base.BaseViewHolder

class CustomerAdapter(private val data: MutableList<PostalDetails> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder>() {

  override fun getItemCount() = data.size

  override fun onBindViewHolder(
    holder: BaseViewHolder,
    position: Int
  ) = holder.onBind(position)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = ViewHolder(
      RowViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  )

  internal fun addDataToList(data: List<PostalDetails>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(private val binding: RowViewUserBinding) : BaseViewHolder(binding.root) {

    override fun clear() {
      binding.apply {
        postName.text = ""
        postPhone.text = ""
      }
    }

    override fun onBind(position: Int) {

      val model = data[position]
      binding.apply {
        postName.text = model.district
        postPhone.text = model.postOffice
      }
    }
  }
}

