package com.poly.poly_sender_android.ui.attributes.creationAttribute

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeParamBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeParamFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeParamBinding? = null
    private val binding get() = _binding!!

    private val args: CreationAttributeParamFragmentArgs by navArgs()
    private var attribute: Attribute? = null

    lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationAttributeParamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.CreationNextBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(creationAttributeSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationAttributeParamFragment)
        }

        attribute = args.attribute
        if (attribute != null) {
            creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.SetSharedStorage(
                attribute!!
            ))
        }
        creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSections)

        binding.editTextAttributeName.setText(creationAttributeSharedViewModel.nmState.selectedName)
        binding.editTextViewSection.setText(creationAttributeSharedViewModel.nmState.selectedSection)

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.nextEvent) {
                    mainActivityViewModel.triggerNext(false)

                    when {
                        binding.editTextAttributeName.string() == "" -> binding.textFieldAttributeName.error =
                            "This field can't be empty" //TODO export to resource and make constants error
                        binding.editTextViewSection.string() == "" -> binding.menuSection.error =
                            "This field can't be empty" //TODO export to resource and make constants error
                        else -> {
                            creationAttributeSharedViewModel.obtainWish(
                                CreationAttributeWish.UpdateSharedStorageByParam(
                                    binding.editTextAttributeName.string(),
                                    binding.editTextViewSection.string()
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
        if (state.isEdit) {
            binding.editTextAttributeName.setText(creationAttributeSharedViewModel.nmState.selectedName)
            binding.editTextViewSection.setText(creationAttributeSharedViewModel.nmState.selectedSection)
        }

        adapter = ArrayAdapter(requireContext(), R.layout.list_item)
        adapter.addAll(state.sections.map { it.sectionName })
        (binding.menuSection.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

