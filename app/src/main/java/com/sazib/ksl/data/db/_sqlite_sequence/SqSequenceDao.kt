package com.sazib.ksl.data.db._sqlite_sequence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SqSequenceDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(sqSequence: List<SqSequence>)

  @Query("SELECT * FROM SqSequence ORDER BY id DESC LIMIT 10") fun loadAll(): List<SqSequence>

  @Query("DELETE FROM SqSequence") fun deleteAll()

}