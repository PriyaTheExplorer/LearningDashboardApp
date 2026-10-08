package com.example.learningdashboardapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboardapp.data.CourseDatabase
import com.example.learningdashboardapp.data.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = CourseDatabase.getDatabase(application)

    private val repository = CourseRepository(
        database.courseDao()
    )

    private val _uiState =
        MutableStateFlow<CourseUiState>(CourseUiState.Loading)

    val uiState: StateFlow<CourseUiState> = _uiState

    init {
        observeCourses()
    }

    private fun observeCourses() {

        viewModelScope.launch {

            repository.observeCourses()
                .catch { exception ->

                    _uiState.value = CourseUiState.Error(
                        exception.message ?: "Unable to load courses"
                    )
                }
                .collect { courses ->

                    _uiState.value =
                        if (courses.isEmpty()) {
                            CourseUiState.Empty
                        } else {
                            CourseUiState.Success(courses)
                        }
                }
        }
    }

    fun loadCourses() {

        viewModelScope.launch {

            _uiState.value = CourseUiState.Loading

            try {

                repository.refreshCourses()

            } catch (e: Exception) {

                // Room continues to provide cached data.
                // If cache is empty, show the error.
                val currentState = _uiState.value

                if (currentState is CourseUiState.Empty) {
                    _uiState.value = CourseUiState.Error(
                        e.message ?: "Unable to load courses"
                    )
                }
            }
        }
    }
}