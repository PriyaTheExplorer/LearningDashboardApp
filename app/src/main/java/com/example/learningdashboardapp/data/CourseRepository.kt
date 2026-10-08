package com.example.learningdashboardapp.data

import com.example.learningdashboardapp.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository(
    private val courseDao: CourseDao
) {

    fun observeCourses(): Flow<List<Course>> {

        return courseDao.observeCourses().map { entities ->

            entities.map { entity ->

                Course(
                    id = entity.id,
                    title = entity.title,
                    instructor = entity.instructor,
                    progress = entity.progress,
                    lessons = entity.lessons
                )
            }
        }
    }

    suspend fun refreshCourses() {

        val courses = RetrofitClient.api.getCourses()

        val entities = courses.map { course ->

            CourseEntity(
                id = course.id,
                title = course.title,
                instructor = course.instructor,
                progress = course.progress,
                lessons = course.lessons
            )
        }

        courseDao.insertCourses(entities)
    }

    suspend fun updateCourseProgress(
        courseId: String,
        progress: Int
    ) {
        courseDao.updateProgress(
            courseId = courseId,
            progress = progress
        )
    }
}