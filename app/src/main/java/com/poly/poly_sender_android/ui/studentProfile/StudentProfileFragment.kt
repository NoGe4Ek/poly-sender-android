package com.poly.poly_sender_android.ui.studentProfile

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.databinding.FragmentStudentProfileBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentAttributesAdapter
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileNews
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileState
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentProfileFragment : Fragment(), MviView<StudentProfileState, StudentProfileNews> {

    @Inject
    lateinit var logger: Logger

    private val userDetailsViewModel: StudentProfileViewModel by viewModels()

    private var _binding: FragmentStudentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var studentAttributesRecycler: RecyclerView
    lateinit var studentAttributesAdapter: StudentAttributesAdapter

    private val args: StudentProfileFragmentArgs by navArgs()
    private lateinit var student: Student

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

        App.appBar = AppBar.StudentBar
        App.mCurrentActivity.invalidateOptionsMenu()

        studentAttributesRecycler = binding.studentAttributesList
        studentAttributesAdapter = StudentAttributesAdapter()
        studentAttributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        studentAttributesRecycler.adapter = studentAttributesAdapter

        with(userDetailsViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@StudentProfileFragment)
        }

        binding.cardViewStudentProfile.setBackgroundResource(R.drawable.ic_card_profile_background)

        userDetailsViewModel.obtainWish(StudentProfileWish.SetStudent(args.student))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: StudentProfileState) {
        if (state.isLoading) {
            //TODO
        }

        if (state.student != null) {
            state.student.apply {
                binding.textViewSquareAvatarText.text =
                    name.split(" ").joinToString(separator = "") { w -> w[0].toString() }.dropLast(1)
                binding.textViewStudentProfileName.text = name
                binding.textViewStudentProfileRole.text = "Студент"
                studentAttributesAdapter.submitList(state.student.attributes.filter { it.attributeValues.isNotEmpty() })
            }
        }
    }

    override fun renderNews(new: StudentProfileNews) {
        when (new) {
            is StudentProfileNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}