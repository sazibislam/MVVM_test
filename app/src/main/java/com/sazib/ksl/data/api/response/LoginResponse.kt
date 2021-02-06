package com.sazib.ksl.data.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SigninResponse(
  @SerializedName("status") @Expose var status: String? = null,
  @SerializedName("token") @Expose var token: String? = null,
  @SerializedName("user") @Expose var user: User? = null,
  @SerializedName("error") @Expose var error: Error? = null
)

data class User(
  @SerializedName("address") @Expose var address: String? = null,
  @SerializedName("created_at") @Expose var createdAt: String? = null,
  @SerializedName("email") @Expose var email: String? = null,
  @SerializedName("first_name") @Expose var firstName: String? = null,
  @SerializedName("gender") @Expose var gender: String? = null,
  @SerializedName("id") @Expose var id: Int? = null,
  @SerializedName("image") @Expose var image: String? = null,
  @SerializedName("last_name") @Expose var lastName: String? = null,
  @SerializedName("phone") @Expose var phone: String? = null,
  @SerializedName("role") @Expose var role: String? = null,
  @SerializedName("updated_at") @Expose var updatedAt: String? = null,
)