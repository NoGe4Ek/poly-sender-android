package com.poly.poly_sender_android.ui.attributes.creationSection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentCreationSectionBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.CreationSectionNews
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.CreationSectionState
import com.poly.poly_sender_android.ui.attributes.creationSection.mvi.CreationSectionWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.util.MessageConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreationSectionFragment : Fragment(), MviView<CreationSectionState, CreationSectionNews> {

    @Inject
    lateinit var logger: Logger

    private val creationSectionViewModel: CreationSectionViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationSectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.CreationApplyBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(creationSectionViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationSectionFragment)
        }

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.applyEvent) {
                    mainActivityViewModel.triggerApply(false)

                    if (binding.editTextSectionName.string() == "") {
                        binding.textFieldSectionName.error = MessageConstants.ERROR_EMPTY_FILL
                    } else {
                        creationSectionViewModel.obtainWish(
                            CreationSectionWish.CreateSection(
                                binding.editTextSectionName.string()
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationSectionState) {
        if (state.isLoading) {
            //TODO loading
        }
    }

    override fun renderNews(new: CreationSectionNews) {
        when (new) {
            is CreationSectionNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
