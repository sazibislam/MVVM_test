package com.sazib.ksl.data.db._user

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
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
  @ColumnInfo(name = "username") var username: String? = null,

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
) : Parcelable {
  constructor(parcel: Parcel) : this(
      parcel.readValue(Int::class.java.classLoader) as? Int,
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
      parcel.readValue(Long::class.java.classLoader) as? Long,
      parcel.readValue(Long::class.java.classLoader) as? Long
  )

  override fun writeToParcel(
    parcel: Parcel,
    flags: Int
  ) {
    parcel.writeValue(id)
    parcel.writeString(username)
    parcel.writeString(email)
    parcel.writeString(passwordhash)
    parcel.writeValue(isactive)
    parcel.writeValue(createdAt)
    parcel.writeValue(updatedAt)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}
