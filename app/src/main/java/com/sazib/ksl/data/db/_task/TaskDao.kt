package com.sazib.ksl.data.db._task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAll(task: List<Task>)
  @Query("SELECT * FROM Task ORDER BY id DESC LIMIT 10") fun loadAll(): List<Task>
  @Delete fun deleteTask(task: List<Task>)
  @Query("DELETE FROM Task") fun deleteAll()
}