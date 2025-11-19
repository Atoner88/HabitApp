package com.atoner.habitapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.atoner.habitapp.data.database.HabitDatabase
import com.atoner.habitapp.data.model.HabitCategory
import com.atoner.habitapp.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository
    val allCategories: LiveData<List<HabitCategory>>

    init {
        val database = HabitDatabase.getDatabase(application)
        val categoryDao = database.habitCategoryDao()
        repository = CategoryRepository(categoryDao)
        allCategories = repository.getAllCategories().asLiveData()
    }

    fun insertCategory(category: HabitCategory) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    fun updateCategory(category: HabitCategory) = viewModelScope.launch {
        repository.updateCategory(category)
    }

    fun deleteCategory(category: HabitCategory) = viewModelScope.launch {
        repository.deleteCategory(category)
    }

    suspend fun getHabitCountForCategory(categoryId: Long): Int {
        return repository.getHabitCountForCategory(categoryId)
    }
}
