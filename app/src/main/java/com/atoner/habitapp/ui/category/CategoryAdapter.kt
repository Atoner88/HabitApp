package com.atoner.habitapp.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.data.model.HabitCategory
import com.atoner.habitapp.util.ColorUtils

class CategoryAdapter(
    private val onCategoryClick: (HabitCategory) -> Unit,
    private val onCategoryDelete: (HabitCategory) -> Unit
) : ListAdapter<HabitCategory, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, onCategoryClick, onCategoryDelete)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.categoryCardView)
        private val nameTextView: TextView = itemView.findViewById(R.id.categoryName)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(
            category: HabitCategory,
            onCategoryClick: (HabitCategory) -> Unit,
            onCategoryDelete: (HabitCategory) -> Unit
        ) {
            nameTextView.text = category.name
            cardView.setCardBackgroundColor(category.color)
            
            // Set text color based on background
            val textColor = ColorUtils.getContrastColor(category.color)
            nameTextView.setTextColor(textColor)

            cardView.setOnClickListener {
                onCategoryClick(category)
            }

            deleteButton.setOnClickListener {
                onCategoryDelete(category)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<HabitCategory>() {
        override fun areItemsTheSame(oldItem: HabitCategory, newItem: HabitCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HabitCategory, newItem: HabitCategory): Boolean {
            return oldItem == newItem
        }
    }
}
