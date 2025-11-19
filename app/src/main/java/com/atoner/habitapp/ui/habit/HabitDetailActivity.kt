package com.atoner.habitapp.ui.habit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.atoner.habitapp.R
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitCategory
import com.atoner.habitapp.viewmodel.CategoryViewModel
import com.atoner.habitapp.viewmodel.HabitViewModel
import kotlinx.coroutines.launch

class HabitDetailActivity : AppCompatActivity() {
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var currentStreakTextView: TextView
    private lateinit var highestStreakTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    
    private var habitId: Long? = null
    private var currentHabit: Habit? = null
    private var categories = listOf<HabitCategory>()

    companion object {
        const val EXTRA_HABIT_ID = "habit_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize ViewModels
        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Initialize views
        nameEditText = findViewById(R.id.habitNameEditText)
        descriptionEditText = findViewById(R.id.habitDescriptionEditText)
        categorySpinner = findViewById(R.id.categorySpinner)
        currentStreakTextView = findViewById(R.id.currentStreakTextView)
        highestStreakTextView = findViewById(R.id.highestStreakTextView)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        // Get habit ID from intent
        habitId = intent.getLongExtra(EXTRA_HABIT_ID, -1).takeIf { it != -1L }

        // Observe categories
        categoryViewModel.allCategories.observe(this) { categoryList ->
            categories = categoryList
            setupCategorySpinner()
            
            // Load habit data if editing
            habitId?.let { id ->
                loadHabitData(id)
            }
        }

        // Setup buttons
        saveButton.setOnClickListener {
            saveHabit()
        }

        deleteButton.setOnClickListener {
            confirmDelete()
        }

        // Show/hide delete button
        deleteButton.visibility = if (habitId != null) android.view.View.VISIBLE else android.view.View.GONE
        
        // Update title
        title = if (habitId != null) "Edit Habit" else "New Habit"
    }

    private fun setupCategorySpinner() {
        val categoryNames = mutableListOf("No Category")
        categoryNames.addAll(categories.map { it.name })
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun loadHabitData(id: Long) {
        lifecycleScope.launch {
            habitViewModel.getHabitWithCompletions(id).collect { habitWithCompletions ->
                currentHabit = habitWithCompletions.habit
                val habit = habitWithCompletions.habit
                
                nameEditText.setText(habit.name)
                descriptionEditText.setText(habit.description)
                currentStreakTextView.text = "Current Streak: ${habit.currentStreak} days"
                highestStreakTextView.text = "Highest Streak: ${habit.highestStreak} days"
                
                // Set category spinner
                if (habit.categoryId != null) {
                    val categoryIndex = categories.indexOfFirst { it.id == habit.categoryId }
                    if (categoryIndex >= 0) {
                        categorySpinner.setSelection(categoryIndex + 1) // +1 for "No Category"
                    }
                }
            }
        }
    }

    private fun saveHabit() {
        val name = nameEditText.text.toString().trim()
        if (name.isEmpty()) {
            nameEditText.error = "Name is required"
            return
        }

        val description = descriptionEditText.text.toString().trim()
        val categoryPosition = categorySpinner.selectedItemPosition
        val categoryId = if (categoryPosition > 0) {
            categories[categoryPosition - 1].id
        } else {
            null
        }

        val habit = if (habitId != null && currentHabit != null) {
            // Update existing habit
            currentHabit!!.copy(
                name = name,
                description = description,
                categoryId = categoryId
            )
        } else {
            // Create new habit
            Habit(
                name = name,
                description = description,
                categoryId = categoryId
            )
        }

        if (habitId != null) {
            habitViewModel.updateHabit(habit)
        } else {
            habitViewModel.insertHabit(habit)
        }

        finish()
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Delete Habit")
            .setMessage("Are you sure you want to delete this habit? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                currentHabit?.let { habit ->
                    habitViewModel.deleteHabit(habit)
                    finish()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
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
