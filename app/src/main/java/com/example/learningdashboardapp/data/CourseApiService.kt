package com.example.learningdashboardapp.data

import com.example.learningdashboardapp.model.Course
import retrofit2.http.GET

interface CourseApiService {
    @GET("courses")
    suspend fun getCourses(): List<Course>
}