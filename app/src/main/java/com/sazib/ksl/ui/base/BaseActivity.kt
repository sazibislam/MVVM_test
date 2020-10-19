package com.sazib.ksl.ui.base

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sazib.ksl.ui.base.BaseFragment.CallBack

abstract class BaseActivity : AppCompatActivity(), KSLView, CallBack {

  override fun goBack() = super.onBackPressed()

  override fun finishIt() = finish()

  override fun onFragmentAttached() {

  }

  override fun onFragmentDetached(tag: String) {

  }

  private fun makeAlert(
    title: String?,
    message: String,
    okListener: DialogInterface.OnClickListener?,
    noListener: DialogInterface.OnClickListener?
  ) {
    try {
      val builder = AlertDialog.Builder(this)

      if (title != null) {
        builder.setTitle(title)
      }
      builder.setCancelable(false)
      builder.setMessage(message.toSpanned())

      if (okListener != null) {
        builder.setPositiveButton(android.R.string.ok, okListener)
      }

      if (noListener != null) {
        builder.setNegativeButton(android.R.string.cancel, noListener)
      }

      val alert = builder.create()
      alert.setCancelable(false)
      alert.show()
    } catch (e: Exception) {
      Log.e("showMessage: %s", e.message ?: "")
    }

  }

  fun showAlert(
    title: String,
    message: String
  ) {
    makeAlert(title, message, NoAction(), null)
  }

  internal inner class NoAction : DialogInterface.OnClickListener {
    override fun onClick(
      dialog: DialogInterface,
      which: Int
    ) {

    }
  }

  private fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
      @Suppress("DEPRECATION") return Html.fromHtml(this)
    }
  }
}
