package com.example.learningdashboardapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningdashboardapp.R
import com.example.learningdashboardapp.model.Course

class CourseAdapter(
    private var courses: List<Course>,
    private val onContinueClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView =
            itemView.findViewById(R.id.tvCourseTitle)

        val instructor: TextView =
            itemView.findViewById(R.id.tvInstructor)

        val progress: TextView =
            itemView.findViewById(R.id.tvProgress)

        val lessons: TextView =
            itemView.findViewById(R.id.tvLessons)

        val continueButton: Button =
            itemView.findViewById(R.id.btnContinue)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CourseViewHolder,
        position: Int
    ) {
        val course = courses[position]

        holder.title.text = course.title
        holder.instructor.text = "Instructor: ${course.instructor}"
        holder.progress.text = "Progress: ${course.progress}%"
        holder.lessons.text = "${course.lessons} Lessons"

        holder.continueButton.setOnClickListener {
            onContinueClick(course)
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}