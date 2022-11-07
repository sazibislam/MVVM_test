package com.sazib.ksl.ui._registration.signup

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.core.util.Supplier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sazib.ksl.R
import com.sazib.ksl.data.AppDataManager
import com.sazib.ksl.data.db._user.User
import com.sazib.ksl.ui._registration.signin.SigninActivity
import com.sazib.ksl.ui.base.BaseActivity
import com.sazib.ksl.ui.base.ViewModelProviderFactory
import com.sazib.ksl.utils.Status.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.CoroutineContext

class RegisterActivity : BaseActivity(), OnClickListener, CoroutineScope {

  private lateinit var vm: RegisterActivityVM
  private var gpsTimer: Timer? = null

  private var job: Job = Job()
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  companion object {
    const val TAG: String = "register_up_activity"
    fun getStartIntent(
      context: Context,
      tag: String
    ): Intent = Intent(context, RegisterActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    initView()
  }

  private fun initView() {

    btnSubmit.setOnClickListener(this@RegisterActivity)

    val supplier = Supplier { RegisterActivityVM(AppDataManager.getInstance().appDbHelper) }
    val factory = ViewModelProviderFactory(RegisterActivityVM::class.java, supplier)
    vm = ViewModelProvider(this, factory).get<RegisterActivityVM>(RegisterActivityVM::class.java)

    vm.getUserData()
      .observe(this, Observer {
        when (it.status) {
          SUCCESS -> {
            //show success dialog
            startActivity(SigninActivity.getStartIntent(this@RegisterActivity, TAG))
            finishIt()
          }
          LOADING -> {
          }
          ERROR -> {
            //show error dialog
          }
        }
      })
  }

  private fun validation() {

    var isValidationOk = true
    var message = "Validation Failed"

    if (inputPassword.text.isNullOrEmpty() && inputPassword.textSize < 6 && inputPassword.textSize > 11) {
      isValidationOk = false
      message = "password is between 6 to 11"
    }

    if (inputEmail.text.isNullOrEmpty()) {
      isValidationOk = false
      message = "Email is Empty"
    }

    if (inputName.text.isNullOrEmpty() && inputName.textSize > 4) {
      isValidationOk = false
      message = "Name Minimum 4 characters but digit not allowed"
    }

    when (isValidationOk) {
      true -> {
        val userData = User()
        userData.createdAt = System.currentTimeMillis()
        userData.updatedAt = System.currentTimeMillis()
        userData.id = null
        userData.username = inputName.text.toString()
        userData.passwordhash = inputPassword.text.toString()
        userData.email = inputEmail.text.toString()

        launch { vm.updateItemData(userData) }
      }
      else -> showAlert("Test Fail", message)
    }
  }

  override fun onClick(v: View?) {

    when (v?.id) {
      R.id.btnSubmit -> {
        startRecording()
        // validation()
/*        val intent = Intent()
        intent.putExtra("value", "test")

        setResult(78, intent)
        super.onBackPressed()*/
      }
    }
  }

  //define the listener
  private val locationListener: LocationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
      Log.d(TAG , "${location.longitude} : ${location.latitude}")
    }
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
      Log.d("onStatusChanged" , "provider : $provider")
    }

    override fun onProviderEnabled(provider: String) {
      Log.d("onProviderEnabled" , "provider : $provider")
    }
    override fun onProviderDisabled(provider: String) {
      Log.d("onProviderDisabled" , "provider : $provider")
    }
  }

  private fun startRecording() {

    gpsTimer?.cancel()
    gpsTimer = Timer()

    val checkInterval: Long = 10000L
    val minDistance: Long = 5000L
    // receive updates
    val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

    try {
      // Request location updates
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
    } catch(ex: SecurityException) {
      Log.d(TAG, "Security Exception, no location available")
    }

    // start the gps receiver thread
    gpsTimer?.scheduleAtFixedRate(object : TimerTask() {
      override fun run() {
        val location: Location? = getBestLocation()
        doLocationUpdate(location, false)
      }
    }, 0, checkInterval)
  }

  fun doLocationUpdate(l: Location?, force: Boolean) {

    Log.d(TAG, "latitude ${l?.latitude}, longitude ${l?.longitude}")
    // val minDistance: Long = getMinDistanceFromPrefs()
    // Log.d(TAG, "update received:$l")
    // if (l == null) {
    //   Log.d(TAG, "Empty location")
    //   if (force) Toast.makeText(
    //     this, "Current location not available",
    //     Toast.LENGTH_SHORT
    //   ).show()
    //   return
    // }
    // if (lastLocation != null) {
    //   val distance = l.distanceTo(lastLocation)
    //   Log.d(TAG, "Distance to last: $distance")
    //   if (l.distanceTo(lastLocation) < minDistance && !force) {
    //     Log.d(TAG, "Position didn't change")
    //     return
    //   }
    //   if (l.accuracy >= lastLocation.getAccuracy() && l.distanceTo(lastLocation) < l.accuracy && !force) {
    //     Log.d(
    //       TAG, "Accuracy got worse and we are still "
    //         + "within the accuracy range.. Not updating"
    //     )
    //     return
    //   }
    //
    //   if (l.time <= lastprovidertimestamp && !force) {
    //     Log.d(TAG, "Timestamp not never than last")
    //     return
    //   }
    // }
    // upload/store your location here
  }
  var TAG = "better_location"
  // private val contest: Context? = null

  private fun getGPSCheckMilliSecsFromPrefs(): Long {
    return 1L
  }

  /**
   * try to get the 'best' location selected from all providers
   */
  fun getBestLocation(): Location? {
    val networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER)
    val gpslocation = getLocationByProvider(LocationManager.GPS_PROVIDER)

    // if we have only one location available, the choice is easy
    if (gpslocation == null) {
      Log.d(TAG, "No GPS Location available.")
      return networkLocation
    }
    if (networkLocation == null) {
      Log.d(TAG, "No Network Location available")
      return gpslocation
    }
    // a locationupdate is considered 'old' if its older than the configured
    // update interval. this means, we didn't get a
    // update from this provider since the last check
    val old = System.currentTimeMillis() - getGPSCheckMilliSecsFromPrefs()
    val gpsIsOld = gpslocation.time < old
    val networkIsOld = networkLocation.time < old
    // gps is current and available, gps is better than network
    if (!gpsIsOld) {
      Log.d(TAG, "Returning current GPS Location")
      return gpslocation
    }
    // gps is old, we can't trust it. use network location
    if (!networkIsOld) {
      Log.d(TAG, "GPS is old, Network is current, returning network")
      return networkLocation
    }
    // both are old return the newer of those two
    return if (gpslocation.time > networkLocation.time) {
      Log.d(TAG, "Both are old, returning gps(newer)")
      gpslocation
    } else {
      Log.d(TAG, "Both are old, returning network(newer)")
      networkLocation
    }
  }

  /**
   * get the last known location from a specific provider (network/gps)
   */
  private fun getLocationByProvider(provider: String): Location? {
    var location: Location? = null
    //if (!isProviderSupported(provider)) {
    if (false) {
      return null
    }
    val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
    try {
      if (locationManager.isProviderEnabled(provider)) {
        location = locationManager.getLastKnownLocation(provider)
      }
    } catch (e: IllegalArgumentException) {
      Log.d(TAG, "Cannot access Provider $provider")
    }
    return location
  }
}
