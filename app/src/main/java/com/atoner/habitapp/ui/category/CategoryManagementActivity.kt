package com.atoner.habitapp.ui.category

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.data.model.HabitCategory
import com.atoner.habitapp.util.ColorUtils
import com.atoner.habitapp.viewmodel.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryManagementActivity : AppCompatActivity() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_management)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Manage Categories"

        // Initialize ViewModel
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Setup RecyclerView
        recyclerView = findViewById(R.id.categoryRecyclerView)
        categoryAdapter = CategoryAdapter(
            onCategoryClick = { category ->
                showEditCategoryDialog(category)
            },
            onCategoryDelete = { category ->
                confirmDeleteCategory(category)
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = categoryAdapter

        // Observe categories
        categoryViewModel.allCategories.observe(this) { categories ->
            categoryAdapter.submitList(categories)
        }

        // Setup FAB
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun showAddCategoryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_category, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.categoryNameEditText)
        
        AlertDialog.Builder(this)
            .setTitle("Add Category")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameEditText.text.toString().trim()
                if (name.isNotEmpty()) {
                    val category = HabitCategory(
                        name = name,
                        color = ColorUtils.getRandomColor()
                    )
                    categoryViewModel.insertCategory(category)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditCategoryDialog(category: HabitCategory) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_category, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.categoryNameEditText)
        nameEditText.setText(category.name)
        
        AlertDialog.Builder(this)
            .setTitle("Edit Category")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameEditText.text.toString().trim()
                if (name.isNotEmpty()) {
                    val updatedCategory = category.copy(name = name)
                    categoryViewModel.updateCategory(updatedCategory)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteCategory(category: HabitCategory) {
        AlertDialog.Builder(this)
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete this category? Habits in this category will not be deleted.")
            .setPositiveButton("Delete") { _, _ ->
                categoryViewModel.deleteCategory(category)
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
