package com.atoner.habitapp.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atoner.habitapp.R
import com.atoner.habitapp.util.DateUtils
import java.time.LocalDate

class CalendarAdapter(
    private val onDateClick: (LocalDate) -> Unit,
    private val isDateCompleted: (LocalDate) -> Boolean
) : ListAdapter<CalendarDay, CalendarAdapter.CalendarViewHolder>(CalendarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarDay = getItem(position)
        holder.bind(calendarDay, onDateClick, isDateCompleted)
    }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)

        fun bind(
            calendarDay: CalendarDay,
            onDateClick: (LocalDate) -> Unit,
            isDateCompleted: (LocalDate) -> Boolean
        ) {
            val date = calendarDay.date
            
            if (date == null) {
                dayTextView.text = ""
                dayTextView.setBackgroundColor(Color.TRANSPARENT)
                dayTextView.isClickable = false
            } else {
                dayTextView.text = date.dayOfMonth.toString()
                
                val isToday = DateUtils.isToday(date)
                val isFuture = DateUtils.isFuture(date)
                val isCompleted = isDateCompleted(date)
                
                when {
                    isFuture -> {
                        dayTextView.setBackgroundColor(Color.parseColor("#EEEEEE"))
                        dayTextView.setTextColor(Color.parseColor("#CCCCCC"))
                        dayTextView.isClickable = false
                    }
                    isCompleted -> {
                        dayTextView.setBackgroundColor(Color.parseColor("#4CAF50"))
                        dayTextView.setTextColor(Color.WHITE)
                        dayTextView.isClickable = true
                    }
                    isToday -> {
                        dayTextView.setBackgroundColor(Color.parseColor("#2196F3"))
                        dayTextView.setTextColor(Color.WHITE)
                        dayTextView.isClickable = true
                    }
                    else -> {
                        dayTextView.setBackgroundColor(Color.WHITE)
                        dayTextView.setTextColor(Color.BLACK)
                        dayTextView.isClickable = true
                    }
                }
                
                if (!isFuture) {
                    dayTextView.setOnClickListener {
                        onDateClick(date)
                    }
                }
            }
        }
    }

    class CalendarDiffCallback : DiffUtil.ItemCallback<CalendarDay>() {
        override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
            return oldItem == newItem
        }
    }
}
