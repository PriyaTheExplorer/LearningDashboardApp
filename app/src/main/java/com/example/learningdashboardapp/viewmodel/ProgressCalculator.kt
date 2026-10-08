package com.example.learningdashboardapp.viewmodel

import com.example.learningdashboardapp.model.Lesson
import kotlin.math.roundToInt

object ProgressCalculator {

    fun calculateProgress(
        lessons: List<Lesson>
    ): Int {

        if (lessons.isEmpty()) {
            return 0
        }

        val completedCount =
            lessons.count { it.completed }

        return (
                completedCount * 100.0 / lessons.size
                ).roundToInt()
    }
}