package com.sazib.ksl.data.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDetailsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(userDetails: List<UserDetails>)

  @Query("SELECT * FROM UserDetails ORDER BY id DESC LIMIT 10") fun loadAll(): List<UserDetails>

  @Query("DELETE FROM UserDetails") fun deleteAll()

}