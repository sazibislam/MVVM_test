package com.sazib.ksl.data.db._user_old

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserOld")
data class UserOld(

    @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
    @ColumnInfo(name = "id") var id: Int? = 0,

    @Expose @SerializedName("userid")
    @ColumnInfo(name = "userid") var userid: String? = null,

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
