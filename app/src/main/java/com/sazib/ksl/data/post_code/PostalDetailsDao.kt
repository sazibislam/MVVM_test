package com.sazib.ksl.data.post_code

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostalDetailsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(postalDetails: List<PostalDetails>)

  @Query("SELECT * FROM PostalDetails") fun loadAll(): List<PostalDetails>

  @Query("DELETE FROM PostalDetails") fun deleteAll()

}