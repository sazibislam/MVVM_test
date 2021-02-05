package com.sazib.ksl.data.db._task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Task")
data class Task(

  @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
  @ColumnInfo(name = "id") var id: Int? = 0,

  @Expose @SerializedName("tasktitle")
  @ColumnInfo(name = "tasktitle") var tasktitle: String? = null,

  @Expose @SerializedName("startat")
  @ColumnInfo(name = "startat") var startat: String? = null,

  @Expose @SerializedName("endat")
  @ColumnInfo(name = "endat") var endat: String? = null,

  @Expose @SerializedName("iswholeday")
  @ColumnInfo(name = "iswholeday") var iswholeday: String? = null,

  @Expose @SerializedName("iscompleted")
  @ColumnInfo(name = "iscompleted") var iscompleted: String? = null,

  @Expose @SerializedName("completedat")
  @ColumnInfo(name = "completedat") var completedat: String? = null,

  @Expose @SerializedName("description")
  @ColumnInfo(name = "description") var description: String? = null,

  @Expose @SerializedName("userid")
  @ColumnInfo(name = "userid") var userid: String? = null,

  @Expose @SerializedName("created_at")
  @ColumnInfo(
      name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
  ) var createdAt: Long? = null,

  @Expose @SerializedName("updated_at")
  @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
