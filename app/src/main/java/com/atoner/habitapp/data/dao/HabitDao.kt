package com.atoner.habitapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitWithCategory
import com.atoner.habitapp.data.model.HabitWithCompletions
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getAllHabitsLiveData(): LiveData<List<Habit>>

    @Transaction
    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getAllHabitsWithCategory(): Flow<List<HabitWithCategory>>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitWithCompletions(habitId: Long): Flow<HabitWithCompletions>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitById(habitId: Long): Habit?

    @Query("SELECT * FROM habits WHERE categoryId = :categoryId AND isArchived = 0")
    fun getHabitsByCategory(categoryId: Long): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habits SET isArchived = 1 WHERE id = :habitId")
    suspend fun archiveHabit(habitId: Long)

    @Query("UPDATE habits SET currentStreak = :streak WHERE id = :habitId")
    suspend fun updateCurrentStreak(habitId: Long, streak: Int)

    @Query("UPDATE habits SET highestStreak = :streak WHERE id = :habitId")
    suspend fun updateHighestStreak(habitId: Long, streak: Int)
}
