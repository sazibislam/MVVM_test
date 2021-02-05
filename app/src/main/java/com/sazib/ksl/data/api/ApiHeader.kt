package com.sazib.ksl.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sazib.ksl.di.AppKey
import com.sazib.ksl.di.PackageName
import com.sazib.ksl.di.VersionName
import javax.inject.Inject

class ApiHeader @Inject constructor(internal val authApiHeader: AuthApiHeader) {

  data class AuthApiHeader @Inject constructor(
    @AppKey @Expose @SerializedName("app_key") val _app_key: String,
    @PackageName @Expose @SerializedName("package") val _package: String,
    @VersionName @Expose @SerializedName("version") val _version: String
  )
}