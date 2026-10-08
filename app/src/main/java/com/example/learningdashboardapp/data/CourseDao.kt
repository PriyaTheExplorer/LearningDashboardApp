package com.example.learningdashboardapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    fun observeCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("DELETE FROM courses")
    suspend fun clearCourses()

    @Query("UPDATE courses SET progress = :progress WHERE id = :courseId")
    suspend fun updateProgress(courseId: String, progress: Int)
}