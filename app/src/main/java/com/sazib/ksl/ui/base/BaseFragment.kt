package com.sazib.ksl.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), KSLView {

  private var parentActivity: BaseActivity? = null
  protected var savedInstanceState: Bundle? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is BaseActivity) {
      val activity = context as BaseActivity?
      this.parentActivity = activity
      activity?.onFragmentAttached()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    this.savedInstanceState = savedInstanceState
    setup(view)
  }

  override fun finishIt() = parentActivity?.finishIt()

  fun getBaseActivity() = parentActivity

  interface CallBack {
    fun onFragmentAttached()
    fun onFragmentDetached(tag: String)
  }

  override fun goBack() = parentActivity?.onBackPressed()

  abstract fun setup(view: View)
}