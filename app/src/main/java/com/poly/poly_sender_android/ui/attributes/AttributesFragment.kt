package com.poly.poly_sender_android.ui.attributes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentAttributesBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributeProfile.AttributeProfileFragmentDirections
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesNews
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesState
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesWish
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import com.poly.poly_sender_android.util.AppAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject


@AndroidEntryPoint
class AttributesFragment : Fragment(),
    MviView<AttributesState, AttributesNews> {

    @Inject
    lateinit var logger: Logger

    private val attributesViewModel: AttributesViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentAttributesBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter
    lateinit var adapter: ArrayAdapter<String>
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttributesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.AttributesBar
        App.mCurrentActivity.invalidateOptionsMenu()

        attributesRecycler = binding.attributeList
        attributesAdapter =
            AttributesAdapter(onItemClicked = { attribute, card -> }, //TODO
                onItemLongClicked = { attribute ->
                    val attributeProfileFragment =
                        AttributeProfileFragmentDirections.actionGlobalAttributeProfileFragment(attribute)
                    findNavController().navigate(attributeProfileFragment)
                })
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        attributesRecycler.addItemDecoration(itemDecoration)

        //Fix recycler view padding bottom
        ViewCompat.setOnApplyWindowInsetsListener(attributesRecycler) { view, insets ->
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
            attributesRecycler.updatePadding(bottom = windowInsets)
            insets
        }

        with(attributesViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@AttributesFragment)
        }

        attributesViewModel.obtainWish(AttributesWish.RefreshSections)

        attributesViewModel.obtainWish(
            AttributesWish.RefreshAttributes(
                attributesViewModel.nmState.searchSelectedSection, ""
            )
        )
        binding.menuSection.editText?.setText(
            attributesViewModel.nmState.searchSelectedSection?.sectionName ?: "Выберите раздел"
        )

        binding.menuSectionAuto.setOnItemClickListener { parent, view, position, id ->
            if (adapter.getItem(position) == "Выберите раздел") {
                attributesViewModel.obtainWish(AttributesWish.RefreshAttributes(null, ""))
                attributesViewModel.obtainWish(AttributesWish.RefreshSelectedSection(null))
            } else {
                val selectedSection = attributesViewModel.nmState.searchSections.find { section ->
                    section.sectionName == adapter.getItem(position)
                }
                attributesViewModel.obtainWish(
                    AttributesWish.RefreshAttributes(
                        selectedSection,
                        ""
                    )
                )
                attributesViewModel.obtainWish(AttributesWish.RefreshSelectedSection(selectedSection))
            }
        }

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.searchQueryStateFlow.debounce(300).collect { query ->
                attributesViewModel.obtainWish(
                    AttributesWish.RefreshAttributes(
                        attributesViewModel.nmState.searchSelectedSection,
                        query
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: AttributesState) {
        if (state.isLoading) {
            //TODO loading
        }

        adapter = ArrayAdapter(requireContext(), R.layout.list_item)
        adapter.add("Выберите раздел")
        adapter.addAll(state.searchSections.map { it.sectionName })
        (binding.menuSection.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        attributesAdapter.submitList(state.attributes.toList()) {
            //fix redundant space after attributing
            _binding?.attributeList?.post {
                attributesRecycler.invalidateItemDecorations()
            }
        }
    }

    override fun renderNews(new: AttributesNews) {
        when (new) {
            is AttributesNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}