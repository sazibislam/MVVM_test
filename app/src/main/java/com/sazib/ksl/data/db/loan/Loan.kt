package com.sazib.ksl.data.db.loan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Loan")
data class Loan(

    @Expose @PrimaryKey(autoGenerate = true) @SerializedName("id")
    @ColumnInfo(name = "id") var id: Int? = 0,

    @Expose @SerializedName("amount")
    @ColumnInfo(name = "amount") var amount: Double? = null,

    @Expose @SerializedName("rateofinterest")
    @ColumnInfo(name = "rateofinterest") var rateofinterest: Double? = null,

    @Expose @SerializedName("installment")
    @ColumnInfo(name = "installment") var installment: Double? = null,

    @Expose @SerializedName("created_at")
    @ColumnInfo(
        name = "created_at", defaultValue = "CURRENT_TIMESTAMP"
    ) var createdAt: Long? = null,

    @Expose @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") var updatedAt: Long? = null
)
