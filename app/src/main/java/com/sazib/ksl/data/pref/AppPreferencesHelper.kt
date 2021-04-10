package com.sazib.ksl.data.pref

import android.content.SharedPreferences
import com.sazib.ksl.utils.AppConstants
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(private val mPrefs: SharedPreferences) :
    PreferencesHelper {

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
    }

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