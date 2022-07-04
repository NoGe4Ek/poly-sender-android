package com.poly.poly_sender_android.ui.filters.creationFilter.creationFilterSelection

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeSelectionAttributingBinding
import com.poly.poly_sender_android.databinding.FragmentCreationFilterSelectionAttributingBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterSharedViewModel
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterNews
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CreationFilterSelectionAttributingFragment : Fragment(),
    MviView<CreationFilterState, CreationFilterNews> {

    @Inject
    lateinit var logger: Logger

    private val creationFilterSharedViewModel: CreationFilterSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationFilterSelectionAttributingBinding? = null
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
            FragmentCreationFilterSelectionAttributingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.StudentsAttributing
        App.mCurrentActivity.invalidateOptionsMenu()

        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(onItemClicked = { attribute, card ->
            if (creationFilterSharedViewModel.nmState.searchSelectedAttributes.contains(attribute)) {
                attributesAdapter.setSelectedAttributes(creationFilterSharedViewModel.nmState.searchSelectedAttributes - attribute)
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.DismissAttribute(attribute))
            } else {
                attributesAdapter.setSelectedAttributes(creationFilterSharedViewModel.nmState.searchSelectedAttributes + attribute)
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.SelectAttribute(attribute))
            }
            card.isChecked = !card.isChecked
        }, onItemLongClicked = {
            //TODO
        }) //TODO add attribute to selected list in state & set attribute.isChecked to true OR VISE VERSA
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        attributesAdapter.setSelectedAttributes(creationFilterSharedViewModel.nmState.searchSelectedAttributes)
        attributesRecycler.addItemDecoration(itemDecoration)

        with(creationFilterSharedViewModel) {
            bind(
                viewLifecycleOwner.lifecycleScope,
                this@CreationFilterSelectionAttributingFragment
            )
        }

        creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSections)

        creationFilterSharedViewModel.obtainWish(
            CreationFilterWish.RefreshSearchingAttributesBySelectedSection(
                creationFilterSharedViewModel.nmState.searchSelectedSection
            )
        )
        binding.menuSection.editText?.setText(creationFilterSharedViewModel.nmState.searchSelectedSection?.sectionName ?: "Выберите раздел")

        binding.menuSectionAuto.setOnItemClickListener { parent, view, position, id ->
            if (adapter.getItem(position) == "Выберите раздел") {
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSearchingAttributesBySelectedSection(null))
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSelectedSection(null))
            } else {
                val selectedSection = creationFilterSharedViewModel.nmState.sections.find{ section -> section.sectionName ==  adapter.getItem(position)}
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSearchingAttributesBySelectedSection(selectedSection))
                creationFilterSharedViewModel.obtainWish(CreationFilterWish.RefreshSelectedSection(selectedSection))
            }
        }

        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.applyEvent) {
                    mainActivityViewModel.triggerApply(false)
                    creationFilterSharedViewModel.apply {
                        obtainWish(
                            CreationFilterWish.UpdateSharedStorageBySelectionAttributing(
                                nmState.searchAttributes, nmState.searchSelectedAttributes, nmState.searchSelectedSection
                            )
                        )
                    }
                    findNavController().navigateUp()
                }
                if (state.clearEvent) {
                    mainActivityViewModel.triggerClear(false)
                    creationFilterSharedViewModel.obtainWish(CreationFilterWish.ClearSearchParam)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationFilterState) {
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

    override fun renderNews(new: CreationFilterNews) {
        when (new) {
            is CreationFilterNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}