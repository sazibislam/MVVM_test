package com.sazib.ksl.ui.test

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sazib.ksl.databinding.RowViewTestBinding
import com.sazib.ksl.ui.base.BaseViewHolder
import com.sazib.ksl.utils.TextAppearance

class TestAdapter(private val data: MutableList<TestModel> = ArrayList()) :
  RecyclerView.Adapter<BaseViewHolder>() {

  private var ctx: Context? = null
  private var callback: Callback? = null
  private var textSize: Int = 18


  override fun getItemCount() = data.size

  override fun onBindViewHolder(
    holder: BaseViewHolder,
    position: Int
  ) = holder.onBind(position)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    ctx = parent.context
    return ViewHolder(
      RowViewTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  fun setCallback(callback: Callback) {
    this.callback = callback
  }

  fun addDataToList(data: List<TestModel>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  fun setTextSize(textSize : Int){
    this.textSize = textSize
    notifyDataSetChanged()
  }

  inner class ViewHolder(private val binding: RowViewTestBinding) : BaseViewHolder(binding.root) {

    override fun clear() {
      binding.apply {
        tvTest.text = ""
      }
    }

    override fun onBind(position: Int) {

      val model = data[position]
      binding.apply {
        tvTest.text = "${model.details}"
        binding.tvTest.underLineColor = Color.WHITE
        tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
      }

      binding.root.setOnClickListener {
        binding.tvTest.underLineColor = Color.BLACK
      }
    }
  }

  interface Callback {
    fun onClick(position: Int)
  }
}

