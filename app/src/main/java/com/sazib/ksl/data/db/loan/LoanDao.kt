package com.sazib.ksl.data.db.loan

import androidx.room.*

@Dao
interface LoanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(loan: List<Loan>)

    @Query("SELECT * FROM Loan ")
    fun loadAll(): List<Loan>

    @Delete
    fun deleteLoan(loan: List<Loan>)

    @Query("DELETE FROM Loan")
    fun deleteAll()
}