package com.sazib.ksl.data.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserDetails")
data class UserDetails(

  @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
  @ColumnInfo(name = "id") var id: Int? = 0,

  @Expose @SerializedName("name")
  @ColumnInfo(name = "name") var name: String? = null,

  @Expose @SerializedName("dob")
  @ColumnInfo(name = "dob") var dob: String? = null,

  @Expose @SerializedName("phone")
  @ColumnInfo(name = "phone") var phone: String? = null,

  @Expose @SerializedName("post_code")
  @ColumnInfo(name = "post_code") var postCode: String? = null,

  @Expose @SerializedName("post_office")
  @ColumnInfo(name = "post_office") var postOffice: String? = null,

  @Expose @SerializedName("thana")
  @ColumnInfo(name = "thana") var thana: String? = null,

  @Expose @SerializedName("district")
  @ColumnInfo(name = "district") var district: String? = null,

  @Expose @SerializedName("created_at")
  @ColumnInfo(
      name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
  ) var createdAt: Long? = null,

  @Expose @SerializedName("updated_at")
  @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
