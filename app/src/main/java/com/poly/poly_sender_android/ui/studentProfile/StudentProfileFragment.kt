package com.poly.poly_sender_android.ui.studentProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentStudentProfileBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileNews
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentProfileFragment : Fragment(), MviView<StudentProfileState, StudentProfileNews> {

    @Inject
    lateinit var logger: Logger

    private val userDetailsViewModel: StudentProfileViewModel by viewModels()

    private var _binding: FragmentStudentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(userDetailsViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@StudentProfileFragment)
        }

        //TODO catch args about student, because we don't have fetchStudentById
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: StudentProfileState) {
        //TODO alternative: 1 - catch all students again and find necessary one /OR/ 2 - get args and send it through actor + reducer
    }

    override fun renderNews(new: StudentProfileNews) {
        when (new) {
            is StudentProfileNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}