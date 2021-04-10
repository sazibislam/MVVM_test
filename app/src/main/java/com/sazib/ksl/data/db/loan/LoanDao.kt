package com.sazib.ksl.data.db.loan

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoanDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAll(loan: List<Loan>)
  @Query("SELECT * FROM Loan ") fun loadAll(): List<Loan>
  @Delete fun deleteLoan(loan: List<Loan>)
  @Query("DELETE FROM Loan") fun deleteAll()
}