package com.sazib.ksl.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sazib.ksl.R
import com.sazib.ksl.databinding.BottomSheetDialogBinding
import com.sazib.ksl.ui.todo.add_task.adapter.AddTaskAdapter
import com.sazib.ksl.utils.DataUtils
import com.sazib.ksl.utils.DataUtils.getTaskType
import kotlinx.android.synthetic.main.activity_add_task.rvTaskType

class ItemSearchDialogFragment : BottomSheetDialogFragment(), OnClickListener {

  private lateinit var binding: BottomSheetDialogBinding

  private var mAdapter: AddTaskAdapter? = null
  var recyclerView: RecyclerView? = null
  private var mLayoutManager: LayoutManager? = null

  companion object {
    const val TAG = "item_search_bottom_sheet"
    fun getStartIntent() = ItemSearchDialogFragment()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    dialog.behavior.skipCollapsed = true
    dialog.behavior.state = STATE_HALF_EXPANDED
    dialog.behavior.isFitToContents = false
    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    return dialog
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mAdapter = AddTaskAdapter()
    mLayoutManager = LinearLayoutManager(requireContext())
    mLayoutManager = LinearLayoutManager(requireContext())
    binding.rvCategory.layoutManager = mLayoutManager
    binding.rvCategory.adapter = mAdapter
    binding.rvCategory.itemAnimator = DefaultItemAnimator()

    mAdapter?.addDataToList(getTaskType())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = BottomSheetDialogBinding.inflate(layoutInflater)
    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    return binding.root
  }

  internal fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

  override fun onClick(v: View?) {
  }
}