package com.sazib.ksl.data.pref

interface PreferencesHelper {

    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String?)
    fun getCurrentUserId(): Long?
}