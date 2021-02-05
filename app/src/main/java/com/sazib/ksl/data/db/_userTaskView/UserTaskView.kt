package com.sazib.ksl.data.db._userTaskView

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTaskView")
data class UserTaskView(

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

  @Expose @SerializedName("taskid")
  @ColumnInfo(name = "taskid") var taskid: String? = null,

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

  @Expose @SerializedName("task_created_at")
  @ColumnInfo(name = "task_created_at") var task_created_at: Long? = null,

  @Expose @SerializedName("created_at")
  @ColumnInfo(
      name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
  ) var createdAt: Long? = null,

  @Expose @SerializedName("updated_at")
  @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
