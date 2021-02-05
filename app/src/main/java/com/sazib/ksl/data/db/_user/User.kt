package com.sazib.ksl.data.db._user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class User(

  @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
  @ColumnInfo(name = "id") var id: Int? = 0,

  @Expose @SerializedName("username")
  @ColumnInfo(name = "name") var name: String? = null,

  @Expose @SerializedName("email")
  @ColumnInfo(name = "email") var email: String? = null,

  @Expose @SerializedName("passwordhash")
  @ColumnInfo(name = "passwordhash") var passwordhash: String? = null,

  @Expose @SerializedName("isactive")
  @ColumnInfo(name = "isactive") var isactive: Boolean? = false,

  @Expose @SerializedName("created_at")
  @ColumnInfo(
      name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
  ) var createdAt: Long? = null,

  @Expose @SerializedName("updated_at")
  @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
