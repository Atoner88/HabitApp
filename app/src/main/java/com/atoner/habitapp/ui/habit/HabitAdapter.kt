package com.atoner.habitapp.ui.habit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.data.model.Habit
import com.atoner.habitapp.data.model.HabitWithCategory
import com.atoner.habitapp.util.ColorUtils
import java.time.LocalDate

class HabitAdapter(
    private val onHabitClick: (Habit) -> Unit,
    private val onHabitToggle: (Habit, LocalDate) -> Unit
) : ListAdapter<HabitWithCategory, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habitWithCategory = getItem(position)
        holder.bind(habitWithCategory, onHabitClick, onHabitToggle)
    }

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.habitCardView)
        private val nameTextView: TextView = itemView.findViewById(R.id.habitName)
        private val categoryTextView: TextView = itemView.findViewById(R.id.habitCategory)
        private val streakTextView: TextView = itemView.findViewById(R.id.habitStreak)
        private val todayCheckBox: CheckBox = itemView.findViewById(R.id.todayCheckBox)

        fun bind(
            habitWithCategory: HabitWithCategory,
            onHabitClick: (Habit) -> Unit,
            onHabitToggle: (Habit, LocalDate) -> Unit
        ) {
            val habit = habitWithCategory.habit
            val category = habitWithCategory.category

            nameTextView.text = habit.name
            streakTextView.text = "🔥 ${habit.currentStreak}"

            if (category != null) {
                categoryTextView.text = category.name
                categoryTextView.visibility = View.VISIBLE
                cardView.setCardBackgroundColor(category.color)
                
                // Set text color based on background
                val textColor = ColorUtils.getContrastColor(category.color)
                nameTextView.setTextColor(textColor)
                categoryTextView.setTextColor(textColor)
                streakTextView.setTextColor(textColor)
            } else {
                categoryTextView.visibility = View.GONE
                cardView.setCardBackgroundColor(Color.WHITE)
                nameTextView.setTextColor(Color.BLACK)
                streakTextView.setTextColor(Color.BLACK)
            }

            cardView.setOnClickListener {
                onHabitClick(habit)
            }

            todayCheckBox.setOnCheckedChangeListener(null)
            todayCheckBox.isChecked = false // This should be set based on actual data
            todayCheckBox.setOnCheckedChangeListener { _, _ ->
                onHabitToggle(habit, LocalDate.now())
            }
        }
    }

    class HabitDiffCallback : DiffUtil.ItemCallback<HabitWithCategory>() {
        override fun areItemsTheSame(oldItem: HabitWithCategory, newItem: HabitWithCategory): Boolean {
            return oldItem.habit.id == newItem.habit.id
        }

        override fun areContentsTheSame(oldItem: HabitWithCategory, newItem: HabitWithCategory): Boolean {
            return oldItem == newItem
        }
    }
}
