package com.atoner.habitapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.atoner.habitapp.data.database.HabitDatabase
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitCompletion
import com.atoner.habitapp.data.model.HabitWithCategory
import com.atoner.habitapp.data.model.HabitWithCompletions
import com.atoner.habitapp.data.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HabitRepository

    val allHabits: LiveData<List<Habit>>
    val allHabitsWithCategory: LiveData<List<HabitWithCategory>>

    init {
        val database = HabitDatabase.getDatabase(application)
        val habitDao = database.habitDao()
        val completionDao = database.habitCompletionDao()
        repository = HabitRepository(habitDao, completionDao)
        
        allHabits = repository.getAllHabits().asLiveData()
        allHabitsWithCategory = repository.getAllHabitsWithCategory().asLiveData()
    }

    fun getHabitsByCategory(categoryId: Long): LiveData<List<Habit>> {
        return repository.getHabitsByCategory(categoryId).asLiveData()
    }

    fun getHabitWithCompletions(habitId: Long): Flow<HabitWithCompletions> {
        return repository.getHabitWithCompletions(habitId)
    }

    fun insertHabit(habit: Habit) = viewModelScope.launch {
        repository.insertHabit(habit)
    }

    fun updateHabit(habit: Habit) = viewModelScope.launch {
        repository.updateHabit(habit)
    }

    fun deleteHabit(habit: Habit) = viewModelScope.launch {
        repository.deleteHabit(habit)
    }

    fun archiveHabit(habitId: Long) = viewModelScope.launch {
        repository.archiveHabit(habitId)
    }

    fun toggleHabitCompletion(habitId: Long, date: LocalDate) = viewModelScope.launch {
        repository.toggleHabitCompletion(habitId, date)
    }

    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletion>> {
        return repository.getCompletionsForHabit(habitId)
    }

    suspend fun isHabitCompletedOnDate(habitId: Long, date: LocalDate): Boolean {
        return repository.isHabitCompletedOnDate(habitId, date)
    }
}
