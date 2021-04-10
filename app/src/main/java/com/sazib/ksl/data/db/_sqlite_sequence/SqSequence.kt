package com.sazib.ksl.data.db._sqlite_sequence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "SqSequence")
data class SqSequence(

    @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
    @ColumnInfo(name = "id") var id: Int? = 0,

    @Expose @SerializedName("name")
    @ColumnInfo(name = "name") var name: String? = null,

    @Expose @SerializedName("seq")
    @ColumnInfo(name = "seq") var seq: String? = null,

    @Expose @SerializedName("created_at")
    @ColumnInfo(
        name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
    ) var createdAt: Long? = null,

    @Expose @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
