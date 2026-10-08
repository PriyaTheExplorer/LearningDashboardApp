package com.example.learningdashboardapp

import com.example.learningdashboardapp.model.Lesson
import com.example.learningdashboardapp.viewmodel.ProgressCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculatorTest {

    @Test
    fun calculateProgress_returnsCorrectPercentage() {

        val lessons = listOf(
            Lesson(
                id = 1,
                courseId = "course1",
                title = "Lesson 1",
                completed = true
            ),
            Lesson(
                id = 2,
                courseId = "course1",
                title = "Lesson 2",
                completed = true
            ),
            Lesson(
                id = 3,
                courseId = "course1",
                title = "Lesson 3",
                completed = false
            ),
            Lesson(
                id = 4,
                courseId = "course1",
                title = "Lesson 4",
                completed = false
            )
        )

        val progress =
            ProgressCalculator.calculateProgress(lessons)

        assertEquals(50, progress)
    }
}
