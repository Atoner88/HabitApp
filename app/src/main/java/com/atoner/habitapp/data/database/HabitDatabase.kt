package com.atoner.habitapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atoner.habitapp.data.dao.HabitCategoryDao
import com.atoner.habitapp.data.dao.HabitCompletionDao
import com.atoner.habitapp.data.dao.HabitDao
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitCategory
import com.atoner.habitapp.data.model.HabitCompletion

@Database(
    entities = [
        Habit::class,
        HabitCategory::class,
        HabitCompletion::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitCategoryDao(): HabitCategoryDao
    abstract fun habitCompletionDao(): HabitCompletionDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
