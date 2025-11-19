package com.atoner.habitapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "habits",
    foreignKeys = [
        ForeignKey(
            entity = HabitCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val categoryId: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val currentStreak: Int = 0,
    val highestStreak: Int = 0,
    val isArchived: Boolean = false
)
