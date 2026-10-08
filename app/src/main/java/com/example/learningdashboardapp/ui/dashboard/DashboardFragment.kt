package com.example.learningdashboardapp.ui.dashboard

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
import com.example.learningdashboardapp.databinding.FragmentDashboardBinding
import com.example.learningdashboardapp.model.Course
import com.example.learningdashboardapp.viewmodel.CourseUiState
import com.example.learningdashboardapp.viewmodel.CourseViewModel
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.learningdashboardapp.R

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseViewModel by viewModels()

    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(
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

        setupRecyclerView()
        observeUiState()

        viewModel.loadCourses()
    }

    private fun setupRecyclerView() {

        courseAdapter = CourseAdapter(emptyList()) { course ->

            val bundle = Bundle().apply {
                putString("courseId", course.id)
                putString("courseTitle", course.title)
                putString("instructor", course.instructor)
                putInt("progress", course.progress)
                putInt("lessons", course.lessons)
            }

            findNavController().navigate(
                R.id.action_dashboardFragment_to_courseDetailsFragment,
                bundle
            )
        }

        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
    }

    private fun observeUiState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.uiState.collect { state ->

                    when (state) {

                        CourseUiState.Loading -> {
                            showLoading()
                        }

                        is CourseUiState.Success -> {
                            showCourses(state.courses)
                        }

                        CourseUiState.Empty -> {
                            showEmpty()
                        }

                        is CourseUiState.Error -> {
                            showError(state.message)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewCourses.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE
    }

    private fun showCourses(
        courses: List<Course>
    ) {

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewCourses.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE

        courseAdapter.updateCourses(courses)
    }

    private fun showEmpty() {

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewCourses.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
    }

    private fun showError(message: String) {

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewCourses.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE

        binding.tvError.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}