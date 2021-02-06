package com.sazib.ksl.utils

import com.androidnetworking.error.ANError

data class Resource<out T>(
  val status: Status,
  val data: T?,
  val message: String?,
  val anError: ANError?
) {

  companion object {

    fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null, null)
    fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null, null)
    fun <T> error(
      msg: String,
      data: T?,
      anError: ANError?
    ): Resource<T> = Resource(Status.ERROR, data, msg, anError)
  }
}