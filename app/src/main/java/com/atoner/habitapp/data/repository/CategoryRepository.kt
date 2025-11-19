package com.atoner.habitapp.data.repository

import com.atoner.habitapp.data.dao.HabitCategoryDao
import com.atoner.habitapp.data.model.HabitCategory
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: HabitCategoryDao) {
    
    fun getAllCategories(): Flow<List<HabitCategory>> = categoryDao.getAllCategories()

    suspend fun getCategoryById(categoryId: Long): HabitCategory? = 
        categoryDao.getCategoryById(categoryId)

    suspend fun insertCategory(category: HabitCategory): Long = 
        categoryDao.insertCategory(category)

    suspend fun updateCategory(category: HabitCategory) = 
        categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: HabitCategory) = 
        categoryDao.deleteCategory(category)

    suspend fun getHabitCountForCategory(categoryId: Long): Int = 
        categoryDao.getHabitCountForCategory(categoryId)
}
