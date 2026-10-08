package com.example.learningdashboardapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningdashboardapp.databinding.ItemLessonBinding
import com.example.learningdashboardapp.model.Lesson

class LessonAdapter(
    private var lessons: List<Lesson>,
    private val onLessonChecked: (Lesson, Boolean) -> Unit
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(
        private val binding: ItemLessonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            lesson: Lesson,
            onLessonChecked: (Lesson, Boolean) -> Unit
        ) {

            binding.tvLessonTitle.text = lesson.title

            binding.checkLesson.setOnCheckedChangeListener(null)

            binding.checkLesson.isChecked = lesson.completed

            binding.checkLesson.setOnCheckedChangeListener { _, isChecked ->

                onLessonChecked(
                    lesson,
                    isChecked
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonViewHolder {

        val binding = ItemLessonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LessonViewHolder,
        position: Int
    ) {

        holder.bind(
            lessons[position],
            onLessonChecked
        )
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun updateLessons(newLessons: List<Lesson>) {

        lessons = newLessons

        notifyDataSetChanged()
    }
}