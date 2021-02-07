package com.sazib.ksl.data.db._user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAll(user: List<User>)
  @Query("SELECT * FROM User ORDER BY id DESC LIMIT 10") fun loadAll(): List<User>
  @Query("DELETE FROM User") fun deleteAll()

  @Query("SELECT * FROM User WHERE userName = :userName AND passwordhash = :pass ")
  fun loadUser(
    userName: String,
    pass: String
  ): List<User>
}