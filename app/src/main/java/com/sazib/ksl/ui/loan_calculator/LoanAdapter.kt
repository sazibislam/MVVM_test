package com.sazib.ksl.ui.loan_calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.data.db.loan.Loan
import com.sazib.ksl.databinding.RowViewLoanBinding
import com.sazib.ksl.ui.base.BaseViewHolder

class LoanAdapter(private val data: MutableList<Loan> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder>() {

  override fun getItemCount() = data.size

  override fun onBindViewHolder(
    holder: BaseViewHolder,
    position: Int
  ) = holder.onBind(position)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = ViewHolder(
      RowViewLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  )

  internal fun addDataToList(data: List<Loan>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  inner class ViewHolder(private val binding: RowViewLoanBinding) : BaseViewHolder(binding.root) {

    override fun clear() {
      binding.apply {
        tvInstallment.text = ""
        tvInterest.text = ""
        tvAmount.text = ""
      }
    }

    override fun onBind(position: Int) {

      val model = data[position]
      binding.apply {
        tvInstallment.text = ""
        tvInterest.text = ""
        tvAmount.text = ""
      }
    }
  }
}

