package com.sazib.ksl.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sazib.ksl.R

abstract class BaseDialogView : DialogFragment(), DialogView {

    private var parentActivity: BaseActivity? = null
    protected var savedInstanceState: Bundle? = null

    override fun getTheme() = R.style.dialogStyle

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
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        val transaction = fragmentManager?.beginTransaction()

        val prevFragment = fragmentManager?.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction?.remove(prevFragment)
        }
        transaction?.addToBackStack(null)
        super.show(manager, tag)
    }

    override fun goBack() = parentActivity?.onBackPressed()
    override fun finishIt() = parentActivity?.finishIt()

    fun dismissDialog(tag: String) {
        dismiss()
        parentActivity?.onFragmentDetached(tag)
    }

    abstract fun initView()
}