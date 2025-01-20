package com.example.guessthetime

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Dodanie kolumny `points` z wartością domyślną 0
                database.execSQL("ALTER TABLE users_table ADD COLUMN points INTEGER NOT NULL DEFAULT 0")
                database.execSQL("UPDATE users_table SET points = 0 WHERE points IS NULL")
            }
        }

        fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,  // Zmieniono na UserDatabase
                    "user_database.db"         // Podaj właściwą nazwę bazy danych
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
