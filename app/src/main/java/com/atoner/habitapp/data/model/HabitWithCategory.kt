package com.atoner.habitapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithCategory(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: HabitCategory?
)
