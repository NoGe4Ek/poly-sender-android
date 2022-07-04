package com.poly.poly_sender_android.ui.attributes.creationAttribute

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
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationAttributeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.CreationApplyBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(creationAttributeSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationAttributeFragment)
        }

        binding.textViewAttributeName.text = creationAttributeSharedViewModel.nmState.selectedName
        binding.textViewStudentCount.text =
            creationAttributeSharedViewModel.nmState.selectedStudents.size.toString()
        binding.textViewSection.text = creationAttributeSharedViewModel.nmState.selectedSection

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.applyEvent) {
                    mainActivityViewModel.triggerApply(false)

                    creationAttributeSharedViewModel.apply {
                        if (creationAttributeSharedViewModel.nmState.isEdit) {
                            obtainWish(CreationAttributeWish.UpdateAttribute(
                                nmState.selectedName,
                                nmState.selectedSection,
                                nmState.selectedStudents
                            ))
                        } else {
                            obtainWish(
                                CreationAttributeWish.CreateAttribute(
                                    nmState.selectedName,
                                    nmState.selectedSection,
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

    override fun renderState(state: CreationAttributeState) {
        binding.textViewAttributeName.text = state.selectedName
        binding.textViewStudentCount.text = state.selectedStudents.size.toString()
        binding.textViewSection.text = state.selectedSection
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

