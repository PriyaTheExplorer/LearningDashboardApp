package com.example.learningdashboardapp.model

data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val progress: Int,
    val lessons: Int
)