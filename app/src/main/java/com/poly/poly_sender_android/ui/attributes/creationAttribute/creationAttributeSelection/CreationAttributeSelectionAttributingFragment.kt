package com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
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
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeSelectionAttributingBinding
import com.poly.poly_sender_android.databinding.FragmentStudentsAttributingBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.students.StudentsAttributingFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeSelectionAttributingFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeSelectionAttributingBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter
    lateinit var adapter: ArrayAdapter<String>
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentCreationAttributeSelectionAttributingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.StudentsAttributing
        App.mCurrentActivity.invalidateOptionsMenu()

        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(onItemClicked = { attribute, card ->
            if (creationAttributeSharedViewModel.nmState.searchSelectedAttributes.contains(attribute)) {
                attributesAdapter.setSelectedAttributes(creationAttributeSharedViewModel.nmState.searchSelectedAttributes - attribute)
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.DismissAttribute(attribute))
            } else {
                attributesAdapter.setSelectedAttributes(creationAttributeSharedViewModel.nmState.searchSelectedAttributes + attribute)
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.SelectAttribute(attribute))
            }
            card.isChecked = !card.isChecked
        }, onItemLongClicked = {
            //TODO
        })
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        attributesAdapter.setSelectedAttributes(creationAttributeSharedViewModel.nmState.searchSelectedAttributes)
        attributesRecycler.addItemDecoration(itemDecoration)

        with(creationAttributeSharedViewModel) {
            bind(
                viewLifecycleOwner.lifecycleScope,
                this@CreationAttributeSelectionAttributingFragment
            )
        }

        creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSections)

        creationAttributeSharedViewModel.obtainWish(
            CreationAttributeWish.RefreshSearchingAttributesBySelectedSection(
                creationAttributeSharedViewModel.nmState.searchSelectedSection
            )
        )
        binding.menuSection.editText?.setText(creationAttributeSharedViewModel.nmState.searchSelectedSection?.sectionName ?: "Выберите раздел")

        binding.menuSectionAuto.setOnItemClickListener { parent, view, position, id ->
            if (adapter.getItem(position) == "Выберите раздел") {
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSearchingAttributesBySelectedSection(null))
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSelectedSection(null))
            } else {
                val selectedSection = creationAttributeSharedViewModel.nmState.sections.find{ section -> section.sectionName ==  adapter.getItem(position)}
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSearchingAttributesBySelectedSection(selectedSection))
                creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.RefreshSelectedSection(selectedSection))
            }
        }

        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.applyEvent) {
                    mainActivityViewModel.triggerApply(false)
                    creationAttributeSharedViewModel.apply {
                        obtainWish(
                            CreationAttributeWish.UpdateSharedStorageBySelectionAttributing(
                                nmState.searchAttributes, nmState.searchSelectedAttributes, nmState.searchSelectedSection
                            )
                        )
                    }
                    findNavController().navigateUp()
                }
                if (state.clearEvent) {
                    mainActivityViewModel.triggerClear(false)
                    creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.ClearSearchParam)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        adapter = ArrayAdapter(requireContext(), R.layout.list_item)
        adapter.add("Выберите раздел")
        adapter.addAll(state.sections.map { it.sectionName })
        (binding.menuSection.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        attributesAdapter.submitList(state.searchAttributes.toList()) {
            //fix redundant space after attributing
            _binding?.attributeList?.post {
                attributesRecycler.invalidateItemDecorations()
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

