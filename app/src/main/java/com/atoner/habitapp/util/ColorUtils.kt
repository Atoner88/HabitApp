package com.atoner.habitapp.util

import android.graphics.Color

object ColorUtils {
    val DEFAULT_COLORS = listOf(
        Color.parseColor("#FF6B6B"), // Red
        Color.parseColor("#4ECDC4"), // Teal
        Color.parseColor("#45B7D1"), // Blue
        Color.parseColor("#FFA07A"), // Light Salmon
        Color.parseColor("#98D8C8"), // Mint
        Color.parseColor("#FFD93D"), // Yellow
        Color.parseColor("#95E1D3"), // Light Teal
        Color.parseColor("#F38181"), // Light Red
        Color.parseColor("#AA96DA"), // Purple
        Color.parseColor("#FCBAD3"), // Pink
        Color.parseColor("#A8E6CF"), // Light Green
        Color.parseColor("#FFD6A5")  // Peach
    )

    fun getRandomColor(): Int {
        return DEFAULT_COLORS.random()
    }

    fun getColorByIndex(index: Int): Int {
        return DEFAULT_COLORS[index % DEFAULT_COLORS.size]
    }

    fun isLightColor(color: Int): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        
        // Calculate luminance
        val luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255
        return luminance > 0.5
    }

    fun getContrastColor(color: Int): Int {
        return if (isLightColor(color)) Color.BLACK else Color.WHITE
    }
}
