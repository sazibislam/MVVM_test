package com.sazib.ksl.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sazib.ksl.BuildConfig
import com.sazib.ksl.data.db.user.UserDetails
import com.sazib.ksl.data.db.user.UserDetailsDao
import com.sazib.ksl.data.post_code.PostalDetails
import com.sazib.ksl.data.post_code.PostalDetailsDao
import com.sazib.ksl.utils.AppConstants

@Database(
    entities = [(UserDetails::class), (PostalDetails::class)],
    version = BuildConfig.VERSION_CODE,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

  companion object {

    @Volatile private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      synchronized(this) {
        var instance = INSTANCE
        if (instance == null) {
          instance = Room.databaseBuilder(
              context.applicationContext, AppDatabase::class.java, AppConstants.DB_NAME
          )
              .allowMainThreadQueries()
              .fallbackToDestructiveMigration()
              .build()
          INSTANCE = instance
        }
        return instance
      }
    }
  }

  abstract fun userDetailsDao(): UserDetailsDao
  abstract fun postalDetailsDao(): PostalDetailsDao

//  val database =
//    Room.databaseBuilder(applicationContext, AppDatabase::class.java, AppConstants.DB_NAME)
//        .addMigrations(MIGRATION_1_2)
//        .allowMainThreadQueries()
//        .build()
//
//  val MIGRATION_1_2: Migration = object : Migration(2, 3) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//      database.execSQL(
//          "ALTER TABLE users " + " ADD COLUMN last_update INTEGER"
//      )
//    }
//  }
}