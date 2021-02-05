package com.sazib.ksl.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.R
import com.sazib.ksl.data.db.AppDbHelper
import com.sazib.ksl.data.db.user_details.UserDetails
import com.sazib.ksl.data.db.post_code.PostalDetails
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.view_customer.ViewCustomerActivity
import com.sazib.ksl.utils.Status.ERROR
import com.sazib.ksl.utils.Status.LOADING
import com.sazib.ksl.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_register.btnSubmit
import kotlinx.android.synthetic.main.activity_register.inputDistrict
import kotlinx.android.synthetic.main.activity_register.inputDob
import kotlinx.android.synthetic.main.activity_register.inputName
import kotlinx.android.synthetic.main.activity_register.inputPhoneNumber
import kotlinx.android.synthetic.main.activity_register.inputPostCode
import kotlinx.android.synthetic.main.activity_register.inputPostOffice
import kotlinx.android.synthetic.main.activity_register.inputThana
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: RegisterActivityVM
  private var postalDataList: List<PostalDetails>? = null

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    const val TAG: String = "register_up_activity"
    fun getStartIntent(context: Context): Intent = Intent(context, RegisterActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    initView()
  }

  private fun initView() {

    btnSubmit.setOnClickListener(this@RegisterActivity)

    vm = ViewModelProvider(this, RegisterActivityVMF(AppDbHelper(this@RegisterActivity))).get(
        RegisterActivityVM::class.java
    )

    vm.getUserData()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              //show success dialog
              startActivity(ViewCustomerActivity.getStartIntent(this@RegisterActivity))
              finishIt()
            }
            LOADING -> {
            }
            ERROR -> {
              //show error dialog
            }
          }
        })

    vm.getPostalData()
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              postalDataList = it.data
            }
            LOADING -> {
            }
            ERROR -> {
            }
          }
        })


    inputPostCode.addTextChangedListener(object : TextWatcher {

      override fun afterTextChanged(s: Editable) {

        postalDataList?.let { postData_ ->

          when (val user: PostalDetails? =
            postData_.find { it.postCode.toString() == s.toString() }) {
            null -> {
            }
            else -> {
              inputPostOffice.setText(user.postOffice)
              inputThana.setText(user.thana)
              inputDistrict.setText(user.district)
            }
          }
        }
      }

      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {
      }

      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {

      }
    })

    launch {
      vm.fetchPostalData()
    }
  }

  private fun validation() {

    var isValidationOk = true
    var message = "Validation Failed"

    if (inputDistrict.text.isNullOrEmpty() && inputDistrict.textSize < 2) {
      isValidationOk = false
      message = "Minimum 2 characters but digit not allowed"
    }

    if (inputThana.text.isNullOrEmpty() && inputThana.textSize < 2) {
      isValidationOk = false
      message = "Minimum 2 characters but digit not allowed"
    }

    if (inputPostOffice.text.isNullOrEmpty() && inputPostOffice.textSize < 2) {
      isValidationOk = false
      message = "Minimum 2 characters but digit not allowed"
    }

    if (inputPostCode.text.isNullOrEmpty() && inputPostCode.textSize < 4 && inputPostCode.textSize > 4) {
      isValidationOk = false
      message = "4-digit postal code"
    }

    if (inputPhoneNumber.text.isNullOrEmpty() && inputPhoneNumber.textSize < 11 && inputPhoneNumber.textSize > 11) {
      isValidationOk = false
      message = "11-digit phone number"
    }

    if (inputDob.text.isNullOrEmpty()) {
      isValidationOk = false
      message = "Date of birth should be 18 years before the current date"
    }

    if (inputName.text.isNullOrEmpty() && inputName.textSize > 2) {
      isValidationOk = false
      message = "Name Minimum 2 characters but digit not allowed"
    }

    when (isValidationOk) {
      true -> {
        //dummy loading ui
        //dialog
        //save data to db

        val userData = UserDetails()
        userData.createdAt = System.currentTimeMillis()
        userData.updatedAt = System.currentTimeMillis()
        userData.id = null
        userData.name = inputName.text.toString()
        userData.dob = inputDob.text.toString()
        userData.phone = inputPhoneNumber.text.toString()
        userData.postCode = inputPostCode.text.toString()
        userData.postOffice = inputPostOffice.text.toString()
        userData.thana = inputThana.text.toString()
        userData.district = inputDistrict.text.toString()

        launch { vm.updateItemData(userData) }
      }
      else -> showAlert("KSL", message)
    }
  }

  override fun onClick(v: View?) {

    when (v?.id) {
      R.id.btnSubmit -> validation()
    }
  }
}
