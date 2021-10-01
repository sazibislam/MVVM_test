package com.sazib.ksl.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.sazib.ksl.di.ApplicationContext
import com.sazib.ksl.di.PreferenceInfo
import com.sazib.ksl.utils.AppConstants
import com.sazib.ksl.utils.AppConstants.PREF_NAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    @PreferenceInfo prefName: String = PREF_NAME
) : PreferencesHelper {

  companion object {
    private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
  }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(
        prefName, Context.MODE_PRIVATE
    )

  override fun getAccessToken(): String? {
    return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)
  }

  override fun setAccessToken(accessToken: String?) {
    mPrefs.edit()
        .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
        .apply()
  }

  override fun getCurrentUserId(): Long? {
    val userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX)
    return if (userId == AppConstants.NULL_INDEX) null else userId
  }
}