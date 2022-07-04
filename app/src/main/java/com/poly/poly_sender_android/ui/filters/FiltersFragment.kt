package com.poly.poly_sender_android.ui.filters

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentFiltersBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.FiltersAdapter
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.filterProfile.FilterProfileFragmentDirections
import com.poly.poly_sender_android.ui.filters.mvi.FiltersNews
import com.poly.poly_sender_android.ui.filters.mvi.FiltersState
import com.poly.poly_sender_android.ui.filters.mvi.FiltersWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject


@AndroidEntryPoint
class FiltersFragment : Fragment(),
    MviView<FiltersState, FiltersNews> {

    @Inject
    lateinit var logger: Logger

    private val filtersViewModel: FiltersViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    lateinit var filtersRecycler: RecyclerView
    lateinit var filtersAdapter: FiltersAdapter
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.FiltersBar
        App.mCurrentActivity.invalidateOptionsMenu()

        filtersRecycler = binding.filterList
        filtersAdapter = FiltersAdapter(onItemClicked = {}, //TODO
            onItemLongClicked = { filter ->
                val filterProfileFragment =
                    FilterProfileFragmentDirections.actionGlobalFilterProfileFragment(filter)
                findNavController().navigate(filterProfileFragment)
            })
        filtersRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        filtersRecycler.adapter = filtersAdapter

        filtersRecycler.addItemDecoration(itemDecoration)

        //Fix recycler view padding bottom
        ViewCompat.setOnApplyWindowInsetsListener(filtersRecycler) { view, insets ->
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
            filtersRecycler.updatePadding(bottom = windowInsets)
            insets
        }

        with(filtersViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@FiltersFragment)
        }


        filtersViewModel.obtainWish(FiltersWish.RefreshFilters(""))

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.searchQueryStateFlow.debounce(300).collect { query ->
                filtersViewModel.obtainWish(
                    FiltersWish.RefreshFilters(
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

    override fun renderState(state: FiltersState) {
        if (state.isLoading) {
            //TODO
        }

        filtersAdapter.submitList(state.filters.toList()) {
            //fix redundant space after attributing
            _binding?.filterList?.post {
                filtersRecycler.invalidateItemDecorations()
            }
        }
    }

    override fun renderNews(new: FiltersNews) {
        when (new) {
            is FiltersNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}