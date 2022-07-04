package com.poly.poly_sender_android.ui.filters.creationFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeBinding
import com.poly.poly_sender_android.databinding.FragmentCreationFilterBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterNews
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CreationFilterFragment : Fragment(),
    MviView<CreationFilterState, CreationFilterNews> {

    @Inject
    lateinit var logger: Logger

    private val creationFilterSharedViewModel: CreationFilterSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.CreationApplyBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(creationFilterSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationFilterFragment)
        }

        binding.textViewFilterName.text = creationFilterSharedViewModel.nmState.selectedName
        binding.textViewStudentCount.text =
            creationFilterSharedViewModel.nmState.selectedStudents.size.toString()
        binding.textViewMailingMode.text = creationFilterSharedViewModel.nmState.selectedMailingMode

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.applyEvent) {
                    mainActivityViewModel.triggerApply(false)

                    creationFilterSharedViewModel.apply {
                        if (creationFilterSharedViewModel.nmState.isEdit) {
                            obtainWish(
                                CreationFilterWish.UpdateFilter(
                                nmState.selectedName,
                                nmState.selectedMailingMode,
                                nmState.selectedStudents
                            ))
                        } else {
                            obtainWish(
                                CreationFilterWish.CreateFilter(
                                    nmState.selectedName,
                                    nmState.selectedMailingMode,
                                    nmState.selectedStudents
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationFilterState) {
        binding.textViewFilterName.text = state.selectedName
        binding.textViewStudentCount.text = state.selectedStudents.size.toString()
        binding.textViewMailingMode.text = state.selectedMailingMode
    }

    override fun renderNews(new: CreationFilterNews) {
        when (new) {
            is CreationFilterNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}