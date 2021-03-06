package com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeSelectionBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentsAdapter
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import com.poly.poly_sender_android.util.MessageConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeSelectionFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeSelectionBinding? = null
    private val binding get() = _binding!!

    lateinit var studentsRecycler: RecyclerView
    lateinit var studentsAdapter: StudentsAdapter
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationAttributeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        if (creationAttributeSharedViewModel.nmState.selectedStudents.isEmpty()) {
            App.appBar = AppBar.CreationSelectionBar
        } else {
            App.appBar = AppBar.CreationSelectionSelectedBar
            App.mCurrentActivity.supportActionBar?.title =
                "Selected: ${creationAttributeSharedViewModel.nmState.selectedStudents.size}"
        }
        App.mCurrentActivity.invalidateOptionsMenu()

        studentsRecycler = binding.studentList
        studentsAdapter = StudentsAdapter(
            onItemClicked = { student, card ->
                if (card.isChecked) {
                    creationAttributeSharedViewModel.obtainWish(
                        CreationAttributeWish.DismissStudent(
                            student.id
                        )
                    )
                } else {
                    creationAttributeSharedViewModel.obtainWish(
                        CreationAttributeWish.SelectStudent(
                            student.id
                        )
                    )
                }
                card.isChecked = !card.isChecked
            },
            onItemLongClicked = { student ->
                val studentProfileFragment =
                    StudentProfileFragmentDirections.actionGlobalStudentProfileFragment(student)
                findNavController().navigate(studentProfileFragment)
            })
        studentsRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        studentsRecycler.adapter = studentsAdapter

        studentsRecycler.addItemDecoration(itemDecoration)

        //Fix recycler view padding bottom
        ViewCompat.setOnApplyWindowInsetsListener(studentsRecycler) { view, insets ->
            val windowInsets = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    view.rootWindowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    view.rootWindowInsets?.stableInsetTop ?: 0
                }
                else -> {
                    0
                }
            }
            studentsRecycler.updatePadding(bottom = windowInsets)
            insets
        }

        with(creationAttributeSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationAttributeSelectionFragment)
        }

        //WARN: wish caught 2 times after navigate from fragment with the same VM, but state obtained correctly

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.searchQueryStateFlow.debounce(300).collect { query ->
                creationAttributeSharedViewModel.obtainWish(
                    CreationAttributeWish.RefreshStudents(
                        creationAttributeSharedViewModel.nmState.searchSelectedAttributes,
                        query
                    )
                )
            }
        }

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.nextEvent) {
                    mainActivityViewModel.triggerNext(false)
                    if (creationAttributeSharedViewModel.nmState.selectedStudents.isNotEmpty()) {
                        creationAttributeSharedViewModel.apply {
                            obtainWish(
                                CreationAttributeWish.UpdateSharedStorageBySelection(
                                    nmState.students,
                                    nmState.selectedStudents
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            MessageConstants.ERROR_EMPTY_FILL,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (state.attributingEvent) {
                    val creationAttributeSelectionAttributingFragment =
                        CreationAttributeSelectionFragmentDirections.actionCreationAttributeSelectionFragmentToCreationAttributeSelectionAttributingFragment()
                    findNavController().navigate(creationAttributeSelectionAttributingFragment)
                    mainActivityViewModel.triggerAttributingEvent(false)
                }
                if (state.selectAllEvent) {
                    if (creationAttributeSharedViewModel.nmState.students.size > creationAttributeSharedViewModel.nmState.selectedStudents.size) {
                        creationAttributeSharedViewModel.obtainWish(
                            CreationAttributeWish.SelectStudents(
                                creationAttributeSharedViewModel.nmState.students.map { it.id }
                                    .toSet()
                            )
                        )

                        studentsAdapter.setSelectedStudents(creationAttributeSharedViewModel.nmState.students.map { it.id }
                            .toSet())
                        studentsAdapter.notifyDataSetChanged()
                        mainActivityViewModel.triggerSelectAllEvent(false)
                    } else {
                        creationAttributeSharedViewModel.obtainWish(
                            CreationAttributeWish.DismissStudents(
                                creationAttributeSharedViewModel.nmState.students.map { it.id }
                                    .toSet()
                            )
                        )
                        studentsAdapter.setSelectedStudents(emptySet())
                        studentsAdapter.notifyDataSetChanged()
                        mainActivityViewModel.triggerSelectAllEvent(false)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        if (state.isLoading) {
            //TODO loading
        }

        studentsAdapter.setSelectedStudents(state.selectedStudents)
        studentsAdapter.notifyDataSetChanged()
        if (state.students.size != studentsAdapter.itemCount || state.selectedStudents.size != studentsAdapter.selectedStudents.size) {
            studentsAdapter.submitList(state.students.toList()) {
                //fix redundant space after attributing
                binding.studentList.post {
                    studentsRecycler.invalidateItemDecorations()
                }
            }
        }
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

