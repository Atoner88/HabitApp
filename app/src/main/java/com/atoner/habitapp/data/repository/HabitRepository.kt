package com.atoner.habitapp.data.repository

import com.atoner.habitapp.data.dao.HabitCompletionDao
import com.atoner.habitapp.data.dao.HabitDao
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitCompletion
import com.atoner.habitapp.data.model.HabitWithCategory
import com.atoner.habitapp.data.model.HabitWithCompletions
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao
) {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()

    fun getAllHabitsWithCategory(): Flow<List<HabitWithCategory>> = habitDao.getAllHabitsWithCategory()

    fun getHabitsByCategory(categoryId: Long): Flow<List<Habit>> = habitDao.getHabitsByCategory(categoryId)

    fun getHabitWithCompletions(habitId: Long): Flow<HabitWithCompletions> = habitDao.getHabitWithCompletions(habitId)

    suspend fun getHabitById(habitId: Long): Habit? = habitDao.getHabitById(habitId)

    suspend fun insertHabit(habit: Habit): Long = habitDao.insertHabit(habit)

    suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)

    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    suspend fun archiveHabit(habitId: Long) = habitDao.archiveHabit(habitId)

    // Completion methods
    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletion>> = 
        habitCompletionDao.getCompletionsForHabit(habitId)

    suspend fun toggleHabitCompletion(habitId: Long, date: LocalDate) {
        val dateString = date.format(dateFormatter)
        val existingCompletion = habitCompletionDao.getCompletionForDate(habitId, dateString)
        
        if (existingCompletion != null) {
            habitCompletionDao.deleteCompletion(existingCompletion)
        } else {
            val completion = HabitCompletion(
                habitId = habitId,
                completionDate = dateString
            )
            habitCompletionDao.insertCompletion(completion)
        }
        
        updateStreaks(habitId)
    }

    suspend fun isHabitCompletedOnDate(habitId: Long, date: LocalDate): Boolean {
        val dateString = date.format(dateFormatter)
        return habitCompletionDao.getCompletionForDate(habitId, dateString) != null
    }

    suspend fun updateStreaks(habitId: Long) {
        val habit = habitDao.getHabitById(habitId) ?: return
        
        // Calculate current streak
        var currentStreak = 0
        var checkDate = LocalDate.now()
        
        while (isHabitCompletedOnDate(habitId, checkDate)) {
            currentStreak++
            checkDate = checkDate.minusDays(1)
        }
        
        // Update highest streak if necessary
        val highestStreak = maxOf(habit.highestStreak, currentStreak)
        
        habitDao.updateCurrentStreak(habitId, currentStreak)
        habitDao.updateHighestStreak(habitId, highestStreak)
    }

    suspend fun getCompletionsForDateRange(habitId: Long, startDate: LocalDate, endDate: LocalDate): List<HabitCompletion> {
        return habitCompletionDao.getCompletionsForDateRange(
            habitId,
            startDate.format(dateFormatter),
            endDate.format(dateFormatter)
        )
    }
}
