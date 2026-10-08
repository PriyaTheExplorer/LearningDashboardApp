package com.example.learningdashboardapp.viewmodel

import com.example.learningdashboardapp.model.Course

sealed interface CourseUiState {

    data object Loading : CourseUiState

    data class Success(
        val courses: List<Course>
    ) : CourseUiState

    data object Empty : CourseUiState

    data class Error(
        val message: String
    ) : CourseUiState
}