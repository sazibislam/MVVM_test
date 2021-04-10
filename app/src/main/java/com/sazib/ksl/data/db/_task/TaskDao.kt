package com.sazib.ksl.data.db._task

import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(task: List<Task>)

    @Query("SELECT * FROM Task ORDER BY id DESC LIMIT 10")
    fun loadAll(): List<Task>

    @Delete
    fun deleteTask(task: List<Task>)

    @Query("DELETE FROM Task")
    fun deleteAll()
}