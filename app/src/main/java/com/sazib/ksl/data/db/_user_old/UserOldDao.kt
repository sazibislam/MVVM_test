package com.sazib.ksl.data.db._user_old

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserOldDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(userOld: List<UserOld>)

  @Query("SELECT * FROM UserOld ORDER BY id DESC LIMIT 10") fun loadAll(): List<UserOld>

  @Query("DELETE FROM UserOld") fun deleteAll()

}