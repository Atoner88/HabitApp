package com.atoner.habitapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.ui.category.CategoryManagementActivity
import com.atoner.habitapp.ui.habit.HabitAdapter
import com.atoner.habitapp.ui.habit.HabitDetailActivity
import com.atoner.habitapp.viewmodel.HabitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    
    private var isGridView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        // Setup RecyclerView
        recyclerView = findViewById(R.id.habitRecyclerView)
        habitAdapter = HabitAdapter(
            onHabitClick = { habit ->
                val intent = Intent(this, HabitDetailActivity::class.java)
                intent.putExtra(HabitDetailActivity.EXTRA_HABIT_ID, habit.id)
                startActivity(intent)
            },
            onHabitToggle = { habit, date ->
                habitViewModel.toggleHabitCompletion(habit.id, date)
            },
            onCalendarClick = { habit ->
                val intent = Intent(this, com.atoner.habitapp.ui.calendar.CalendarViewActivity::class.java)
                intent.putExtra(com.atoner.habitapp.ui.calendar.CalendarViewActivity.EXTRA_HABIT_ID, habit.id)
                startActivity(intent)
            }
        )
        
        updateLayoutManager()
        recyclerView.adapter = habitAdapter

        // Observe habits
        habitViewModel.allHabitsWithCategory.observe(this) { habits ->
            habitAdapter.submitList(habits)
        }

        // Setup FAB
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, HabitDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_view -> {
                isGridView = !isGridView
                updateLayoutManager()
                true
            }
            R.id.action_categories -> {
                startActivity(Intent(this, CategoryManagementActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateLayoutManager() {
        recyclerView.layoutManager = if (isGridView) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
    }
}
