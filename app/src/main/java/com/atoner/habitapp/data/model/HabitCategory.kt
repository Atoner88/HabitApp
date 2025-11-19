package com.atoner.habitapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_categories")
data class HabitCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: Int,
    val icon: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
