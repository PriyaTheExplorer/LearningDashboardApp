package com.example.learningdashboardapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdashboardapp.databinding.FragmentCourseDetailsBinding
import com.example.learningdashboardapp.ui.details.LessonAdapter
import com.example.learningdashboardapp.viewmodel.CourseDetailsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine

class CourseDetailsFragment : Fragment() {

    private var _binding: FragmentCourseDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseDetailsViewModel by viewModels()

    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourseDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupCourseDetails()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupCourseDetails() {

        val courseTitle =
            arguments?.getString("courseTitle") ?: ""

        val instructor =
            arguments?.getString("instructor") ?: ""

        val initialProgress =
            arguments?.getInt("progress") ?: 0

        val lessonCount =
            arguments?.getInt("lessons") ?: 0

        val courseId =
            arguments?.getString("courseId") ?: ""

        binding.tvCourseTitle.text = courseTitle
        binding.tvInstructor.text = "Instructor: $instructor"

        viewModel.initialize(
            courseId = courseId,
            lessonCount = lessonCount,
            initialProgress = initialProgress
        )
    }

    private fun setupRecyclerView() {

        lessonAdapter = LessonAdapter(emptyList()) { lesson, completed ->

            viewModel.updateLesson(
                lessonId = lesson.id,
                completed = completed
            )
        }

        binding.recyclerViewLessons.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lessonAdapter
        }
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                launch {

                    viewModel.lessons
                        .combine(viewModel.progress) { lessons, progress ->
                            Pair(lessons, progress)
                        }
                        .collect { (lessons, progress) ->

                            lessonAdapter.updateLessons(lessons)

                            binding.tvProgress.text =
                                "Progress: $progress%"

                            binding.progressCourse.progress =
                                progress
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}