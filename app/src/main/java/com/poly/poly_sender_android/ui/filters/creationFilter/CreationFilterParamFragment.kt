package com.poly.poly_sender_android.ui.filters.creationFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeParamBinding
import com.poly.poly_sender_android.databinding.FragmentCreationFilterParamBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeParamFragmentArgs
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterNews
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.util.MessageConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CreationFilterParamFragment : Fragment(),
    MviView<CreationFilterState, CreationFilterNews> {

    @Inject
    lateinit var logger: Logger

    private val creationFilterSharedViewModel: CreationFilterSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationFilterParamBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationFilterParamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.CreationNextBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(creationFilterSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationFilterParamFragment)
        }

        val args = CreationFilterParamFragmentArgs.fromBundle(arguments!!) // need to clear start arg
        if (args.filter != null) {
            creationFilterSharedViewModel.obtainWish(
                CreationFilterWish.SetSharedStorage(
                    args.filter!!
                )
            )
        } else {
            if (args.start) {
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.ClearSharedStorage)
                requireArguments().remove("start")
            }
        }



        binding.editTextFilterName.setText(creationFilterSharedViewModel.nmState.selectedName)
        binding.editTextViewFilterMode.setText(creationFilterSharedViewModel.nmState.selectedMailingMode)
        creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSections)

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.nextEvent) {
                    mainActivityViewModel.triggerNext(false)

                    when {
                        binding.editTextFilterName.string() == "" -> binding.textFieldFilterName.error = MessageConstants.ERROR_EMPTY_FILL
                        binding.editTextViewFilterMode.string() == "" -> binding.menuMailingMode.error = MessageConstants.ERROR_EMPTY_FILL
                        else -> {
                            creationFilterSharedViewModel.obtainWish(
                                CreationFilterWish.UpdateSharedStorageByParam(
                                    binding.editTextFilterName.string(),
                                    binding.editTextViewFilterMode.string()
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
        binding.editTextFilterName.setText(creationFilterSharedViewModel.nmState.selectedName)
        binding.editTextViewFilterMode.setText(creationFilterSharedViewModel.nmState.selectedMailingMode)

        adapter = ArrayAdapter(requireContext(), R.layout.list_item)
        adapter.addAll(state.mailingModes.map { it })
        adapter.add("no-reply")
        (binding.menuMailingMode.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun renderNews(new: CreationFilterNews) {
        when (new) {
            is CreationFilterNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}