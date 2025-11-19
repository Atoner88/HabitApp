package com.atoner.habitapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atoner.habitapp.data.model.HabitCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCategoryDao {
    @Query("SELECT * FROM habit_categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<HabitCategory>>

    @Query("SELECT * FROM habit_categories ORDER BY name ASC")
    fun getAllCategoriesLiveData(): LiveData<List<HabitCategory>>

    @Query("SELECT * FROM habit_categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): HabitCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: HabitCategory): Long

    @Update
    suspend fun updateCategory(category: HabitCategory)

    @Delete
    suspend fun deleteCategory(category: HabitCategory)

    @Query("SELECT COUNT(*) FROM habits WHERE categoryId = :categoryId")
    suspend fun getHabitCountForCategory(categoryId: Long): Int
}
