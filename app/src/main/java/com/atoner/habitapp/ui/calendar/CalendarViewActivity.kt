package com.atoner.habitapp.ui.calendar

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.util.DateUtils
import com.atoner.habitapp.viewmodel.HabitViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewActivity : AppCompatActivity() {
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var monthYearTextView: TextView
    
    private var currentMonth = YearMonth.now()
    private var selectedHabit: Habit? = null
    private val completedDates = mutableSetOf<LocalDate>()

    companion object {
        const val EXTRA_HABIT_ID = "habit_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Calendar View"

        // Initialize ViewModel
        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        // Initialize views
        monthYearTextView = findViewById(R.id.monthYearTextView)
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)

        // Setup calendar
        setupCalendar()

        // Get habit from intent
        val habitId = intent.getLongExtra(EXTRA_HABIT_ID, -1)
        if (habitId != -1L) {
            loadHabitData(habitId)
        }

        // Navigation buttons
        findViewById<TextView>(R.id.previousMonthButton).setOnClickListener {
            currentMonth = currentMonth.minusMonths(1)
            updateCalendar()
        }

        findViewById<TextView>(R.id.nextMonthButton).setOnClickListener {
            currentMonth = currentMonth.plusMonths(1)
            updateCalendar()
        }
    }

    private fun setupCalendar() {
        calendarAdapter = CalendarAdapter(
            onDateClick = { date ->
                selectedHabit?.let { habit ->
                    habitViewModel.toggleHabitCompletion(habit.id, date)
                }
            },
            isDateCompleted = { date ->
                completedDates.contains(date)
            }
        )
        
        calendarRecyclerView.layoutManager = GridLayoutManager(this, 7)
        calendarRecyclerView.adapter = calendarAdapter
        
        updateCalendar()
    }

    private fun updateCalendar() {
        monthYearTextView.text = "${DateUtils.getMonthName(currentMonth.atDay(1))} ${currentMonth.year}"
        
        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfMonth = currentMonth.atDay(1)
        val dayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Convert to 0-6 (Sun-Sat)
        
        val calendarDays = mutableListOf<CalendarDay>()
        
        // Add empty days for offset
        repeat(dayOfWeek) {
            calendarDays.add(CalendarDay(null, false))
        }
        
        // Add actual days
        for (day in 1..daysInMonth) {
            val date = currentMonth.atDay(day)
            val isCompleted = completedDates.contains(date)
            calendarDays.add(CalendarDay(date, isCompleted))
        }
        
        calendarAdapter.submitList(calendarDays)
    }

    private fun loadHabitData(habitId: Long) {
        lifecycleScope.launch {
            habitViewModel.getHabitWithCompletions(habitId).collect { habitWithCompletions ->
                selectedHabit = habitWithCompletions.habit
                title = "${habitWithCompletions.habit.name} - Calendar"
                
                // Update completed dates
                completedDates.clear()
                habitWithCompletions.completions.forEach { completion ->
                    completedDates.add(DateUtils.parseIsoDate(completion.completionDate))
                }
                
                updateCalendar()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

data class CalendarDay(
    val date: LocalDate?,
    val isCompleted: Boolean
)
