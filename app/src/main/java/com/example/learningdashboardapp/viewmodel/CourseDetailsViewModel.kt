package com.example.learningdashboardapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboardapp.data.CourseDatabase
import com.example.learningdashboardapp.data.CourseRepository
import com.example.learningdashboardapp.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class CourseDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        CourseDatabase.getDatabase(application)

    private val repository =
        CourseRepository(database.courseDao())

    private val _lessons =
        MutableStateFlow<List<Lesson>>(emptyList())

    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _progress =
        MutableStateFlow(0)

    val progress: StateFlow<Int> = _progress

    private var currentCourseId: String = ""

    fun initialize(
        courseId: String,
        lessonCount: Int,
        initialProgress: Int
    ) {

        if (_lessons.value.isNotEmpty()) {
            return
        }

        currentCourseId = courseId

        val completedCount =
            (lessonCount * initialProgress / 100.0).roundToInt()

        val generatedLessons = (1..lessonCount).map { number ->

            Lesson(
                id = number,
                courseId = courseId,
                title = "Lesson $number",
                completed = number <= completedCount
            )
        }

        _lessons.value = generatedLessons

        _progress.value =
            ProgressCalculator.calculateProgress(generatedLessons)
    }

    fun updateLesson(
        lessonId: Int,
        completed: Boolean
    ) {

        val updatedLessons = _lessons.value.map { lesson ->

            if (lesson.id == lessonId) {
                lesson.copy(completed = completed)
            } else {
                lesson
            }
        }

        _lessons.value = updatedLessons

        val updatedProgress =
            ProgressCalculator.calculateProgress(updatedLessons)

        _progress.value = updatedProgress

        viewModelScope.launch {

            repository.updateCourseProgress(
                courseId = currentCourseId,
                progress = updatedProgress
            )
        }
    }
}