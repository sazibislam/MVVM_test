package com.sazib.ksl.data.db._userTaskView

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserTaskViewDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(userTaskView: List<UserTaskView>)

  @Query("SELECT * FROM UserTaskView ORDER BY id DESC LIMIT 10") fun loadAll(): List<UserTaskView>

  @Query("DELETE FROM UserTaskView") fun deleteAll()

}