package com.atoner.habitapp.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

object DateUtils {
    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val displayFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    fun formatDate(date: LocalDate): String {
        return date.format(displayFormatter)
    }

    fun formatDateToIso(date: LocalDate): String {
        return date.format(isoFormatter)
    }

    fun parseIsoDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, isoFormatter)
    }

    fun getDayOfWeekShort(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    fun getDayOfWeekFull(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun getMonthName(date: LocalDate): String {
        return date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun getDaysInMonth(date: LocalDate): Int {
        return date.lengthOfMonth()
    }

    fun getFirstDayOfMonth(date: LocalDate): LocalDate {
        return date.withDayOfMonth(1)
    }

    fun getLastDayOfMonth(date: LocalDate): LocalDate {
        return date.withDayOfMonth(date.lengthOfMonth())
    }

    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }

    fun isFuture(date: LocalDate): Boolean {
        return date.isAfter(LocalDate.now())
    }
}
